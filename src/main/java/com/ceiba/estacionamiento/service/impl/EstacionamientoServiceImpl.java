package com.ceiba.estacionamiento.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento.controllers.errors.CeibaException;
import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.factory.EstacionamientoFactory;
import com.ceiba.estacionamiento.model.Estacionamiento;
import com.ceiba.estacionamiento.model.enumeration.TipoVehiculoEnum;
import com.ceiba.estacionamiento.repository.EstacionamientoRepository;
import com.ceiba.estacionamiento.repository.VehiculoRepository;
import com.ceiba.estacionamiento.service.EstacionamientoService;

@Service
public class EstacionamientoServiceImpl implements EstacionamientoService{

	public static final String LETRA_INCIAL = "A";
	public static final String TIPO_VEHICULO_NO_PERMITIDO = "Este vehiculo no puede ingresar al estacionamiento, solo son permitidos carros y motos";
	public static final String NO_HAY_CUPOS_DISPONIBLES = "Ya no hay mas cupos disponibles para estacionar este tipo de vehiculo";
	public static final String VEHICULO_NO_PUEDE_INGRESAR = "Este vehiculo no puede ingresar dado que no esta en un dia habil";
	public static final String VEHICULO_YA_ESTA_ESTACIONADO = "El vehiculo ya se encuentra dentro del estacionamiento";
	public static final String VEHICULO_NO_EXISTE = "El vehiculo no existe";
	public static final Integer MIN_HORAS_COBRO_DIA = 9;
	
	@Autowired
	private EstacionamientoRepository estacionamientoRepository;
	
	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Autowired
	private EstacionamientoFactory estacionamientoFactory;
	
	@Override
	public List<EstacionamientoDTO> obtenerEstacionamientos() {
		List<Estacionamiento> estacionamientos = estacionamientoRepository.findAll(); 
		return estacionamientoFactory.convertirListaModelo(estacionamientos);
	}

	@Override
	public EstacionamientoDTO obtenerEstacionamientoByPlaca(String placa) {
		Estacionamiento estacionamiento = estacionamientoRepository.buscarEstacionamientoByPlaca(placa); 
		return estacionamientoFactory.convertirModelo(estacionamiento);
	}

	@Override
	public Integer contarVehiculosByTipo(Integer idTipoVehiculo) {
		return estacionamientoRepository.contarVehiculosByTipo(idTipoVehiculo);
	}

	@Override
	public void registrarIngresoEstacionamiento(EstacionamientoDTO estacionamientoDTO) {
		estacionamientoRepository.save(validarSiEsPosibleEstacionar(estacionamientoDTO));
	}
	
	@Override
	public void registrarSalidaEstacionamiento(EstacionamientoDTO estacionamientoDTO) {
		Estacionamiento estacionamiento = estacionamientoFactory.convertirDTO(estacionamientoDTO);
		if (!validarTipoVehiculo(estacionamiento.getVehiculo().getTipoVehiculo())) {
			throw new CeibaException(TIPO_VEHICULO_NO_PERMITIDO);
		}
		Date fechaSalida = new Date();
		estacionamiento.setPrecio(generarReciboSalida(estacionamiento, fechaSalida));
		estacionamiento.setFechaSalida(fechaSalida);
		estacionamientoRepository.save(estacionamiento);
	}
	
	public Estacionamiento validarSiEsPosibleEstacionar(EstacionamientoDTO estacionamientoDTO) {
		Integer idTipoVehiculo = estacionamientoDTO.getVehiculo().getTipoVehiculo();
		if (!validarTipoVehiculo(idTipoVehiculo)) {
			throw new CeibaException(TIPO_VEHICULO_NO_PERMITIDO);
		}
		Estacionamiento estacionamiento = estacionamientoFactory.convertirDTO(estacionamientoDTO);
		String placa = estacionamiento.getVehiculo().getPlaca();
		Integer cantidadVehiculos = contarVehiculosByTipo(idTipoVehiculo);
		if (!validarExistenciaVehiculo(placa)) {
			throw new CeibaException(VEHICULO_NO_EXISTE);
		}
		if (!estacionamiento.validarCantidadVehiculos(cantidadVehiculos)) {
			throw new CeibaException(NO_HAY_CUPOS_DISPONIBLES);
		}
		if (!validarIngresoPorPlaca(placa, estacionamiento.getFechaIngreso())) {
			throw new CeibaException(VEHICULO_NO_PUEDE_INGRESAR);
		}
		if (validarEstaEstacionadoVehiculo(placa)) {
			throw new CeibaException(VEHICULO_YA_ESTA_ESTACIONADO);
		}
		return estacionamiento;
	}
	
	public Boolean validarExistenciaVehiculo(String placa) {
		return vehiculoRepository.findByPlaca(placa) != null;
	}
	
	public Boolean validarTipoVehiculo(Integer idTipoVehiculo) {
		return TipoVehiculoEnum.obtenerTipoVehiculos().stream().anyMatch(t -> t.getId().equals(idTipoVehiculo));
	}
	
	public Boolean validarIngresoPorPlaca(String placa, Date fecha) {
		if (placa.startsWith(LETRA_INCIAL)) {
			Calendar date = Calendar.getInstance();
			date.setTime(fecha);
			Integer diaActual = date.get(Calendar.DAY_OF_WEEK);
			return diaActual != 1 && diaActual != 2;
		}
		return true;
	}
	
	public Boolean validarEstaEstacionadoVehiculo(String placa) {
		return obtenerVehiculoEstacionado(placa) != null;
	}
	
	public EstacionamientoDTO obtenerVehiculoEstacionado(String placa) {
		Estacionamiento estacionamiento = estacionamientoRepository.obtenerVehiculoEstacionado(placa); 
		return estacionamientoFactory.convertirModelo(estacionamiento);
	}

	public Double generarReciboSalida(Estacionamiento estacionamiento, Date fechaSalida) {
		try {
			Long diferenciaFechas = fechaSalida.getTime() - estacionamiento.getFechaIngreso().getTime();
			int horasDiferencia = (int) TimeUnit.HOURS.convert(diferenciaFechas, TimeUnit.MILLISECONDS);
			int diasDiferencia = (int) TimeUnit.DAYS.convert(diferenciaFechas, TimeUnit.MILLISECONDS);
			if (horasDiferencia >= MIN_HORAS_COBRO_DIA) {
				diasDiferencia += 1;
			} else if (horasDiferencia == 0) {
				horasDiferencia = 1;
			}
			return estacionamiento.calcularPrecio(horasDiferencia, diasDiferencia, estacionamiento.getVehiculo().getCilindraje());
		} catch (CeibaException e) {
			throw new CeibaException("Ocurrio un error calculando el precio total a pagar en el estacionamiento " + e);
		}
	}
}
