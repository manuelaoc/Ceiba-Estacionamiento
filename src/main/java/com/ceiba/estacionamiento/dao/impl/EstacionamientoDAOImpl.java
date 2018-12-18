package com.ceiba.estacionamiento.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento.controllers.errors.CeibaException;
import com.ceiba.estacionamiento.dao.EstacionamientoDAO;
import com.ceiba.estacionamiento.model.Estacionamiento;
import com.ceiba.estacionamiento.model.enumeration.TipoVehiculoEnum;
import com.ceiba.estacionamiento.repository.EstacionamientoRepository;

@Service
public class EstacionamientoDAOImpl implements EstacionamientoDAO{

	public static final Integer MAX_CARROS = 20;
	public static final Integer MAX_MOTOS = 10;
	public static final String LETRA_INCIAL = "A";
	public static final String TIPO_VEHICULO_NO_PERMITIDO = "Este vehiculo no puede ingresar al estacionamiento, solo son permitidos carros y motos";
	public static final String NO_HAY_CUPOS_DISPONIBLES = "Ya no hay mas cupos disponibles para estacionar este tipo de vehiculo";
	public static final String VEHICULO_NO_PUEDE_INGRESAR = "Este vehiculo no puede ingresar dado que no esta en un dia habil";
	public static final String VEHICULO_YA_ESTA_ESTACIONADO = "El vehiculo ya se encuentra dentro del estacionamiento";
	public static final Integer VALOR_HORA_CARRO = 1000;
	public static final Integer VALOR_HORA_MOTO = 500;
	public static final Integer VALOR_DIA_CARRO = 8000;
	public static final Integer VALOR_DIA_MOTO = 4000;
	public static final Integer VALOR_CILINDRAJE_MAYOR_500 = 2000;
	public static final Integer MIN_HORAS_COBRO_DIA = 9;
	public static final Integer CILINDRAJE_COBRO_ADICIONAL = 500;
	
	@Autowired
	private EstacionamientoRepository estacionamientoRepository; 
	
	@Autowired
	public EstacionamientoDAOImpl(EstacionamientoRepository estacionamientoRepository){
	  this.estacionamientoRepository = estacionamientoRepository;
	}
	
	@Override
	public void registrarIngresoEstacionamiento(Estacionamiento estacionamiento) {
		if (validarSiEsPosibleEstacionar(estacionamiento)) {
			estacionamientoRepository.save(estacionamiento);
		}
	}

	@Override
	public List<Estacionamiento> obtenerEstacionamientos() {
		return estacionamientoRepository.findAll();
	}

	@Override
	public Estacionamiento obtenerEstacionamientoByPlaca(String placa) {
		return estacionamientoRepository.buscarEstacionamientoByPlaca(placa);
	}

	@Override
	public Integer contarVehiculosByTipo(Integer idTipoVehiculo) {
		return estacionamientoRepository.contarVehiculosByTipo(idTipoVehiculo);
	}

	@Override
	public Estacionamiento obtenerVehiculoEstacionado(String placa) {
		return estacionamientoRepository.obtenerVehiculoEstacionado(placa);
	}
	
	@Override
	public Boolean validarSiEsPosibleEstacionar(Estacionamiento estacionamiento) {
		Integer idTipoVehiculo = estacionamiento.getVehiculo().getTipoVehiculo().getId();
		String placa = estacionamiento.getVehiculo().getPlaca();
		if (!validarTipoVehiculo(idTipoVehiculo)) {
			throw new CeibaException(TIPO_VEHICULO_NO_PERMITIDO);
		}
		if (!validarCantidadVehiculos(idTipoVehiculo)) {
			throw new CeibaException(NO_HAY_CUPOS_DISPONIBLES);
		}
		if (!validarIngresoPorPlaca(placa, estacionamiento.getFechaIngreso())) {
			throw new CeibaException(VEHICULO_NO_PUEDE_INGRESAR);
		}
		if (validarEstaEstacionadoVehiculo(placa)) {
			throw new CeibaException(VEHICULO_YA_ESTA_ESTACIONADO);
		}
		return true;
	}
	
	public Boolean validarTipoVehiculo(Integer idTipoVehiculo) {
		return TipoVehiculoEnum.obtenerTipoVehiculos().stream().anyMatch(t -> t.getId().equals(idTipoVehiculo));
	}
	
	public Boolean validarCantidadVehiculos(Integer idTipoVehiculo) {
		Integer contarVehiculos = contarVehiculosByTipo(idTipoVehiculo);
		if (idTipoVehiculo.equals(TipoVehiculoEnum.CARRO.getId())) {
			return contarVehiculos < MAX_CARROS;
		} else if (idTipoVehiculo.equals(TipoVehiculoEnum.MOTO.getId())) {
			return contarVehiculos < MAX_MOTOS;
		}
		throw new CeibaException(TIPO_VEHICULO_NO_PERMITIDO);
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

	@Override
	public void registrarSalidaEstacionamiento(Estacionamiento estacionamiento) {
		if (!validarTipoVehiculo(estacionamiento.getVehiculo().getTipoVehiculo().getId())) {
			throw new CeibaException(TIPO_VEHICULO_NO_PERMITIDO);
		}
		Date fechaSalida = new Date();
		estacionamiento.setPrecio(generarReciboSalida(estacionamiento, fechaSalida));
		estacionamiento.setFechaSalida(fechaSalida);
		estacionamientoRepository.save(estacionamiento);
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
			return calcularPrecio(estacionamiento, horasDiferencia, diasDiferencia);
		} catch (CeibaException e) {
			throw new CeibaException("Ocurrio un error calculando el precio total a pagar en el estacionamiento " + e);
		}
	}

	private Double calcularPrecio(Estacionamiento estacionamiento, int horasDiferencia, int diasDiferencia) {
		Double precio = 0D;
		Integer idTipoVehiculo = estacionamiento.getVehiculo().getTipoVehiculo().getId();
		if (idTipoVehiculo.equals(TipoVehiculoEnum.CARRO.getId())) {
			precio = diasDiferencia > 0 ? (double) (diasDiferencia * VALOR_DIA_CARRO) : (double) (horasDiferencia * VALOR_HORA_CARRO);
		} else if (idTipoVehiculo.equals(TipoVehiculoEnum.MOTO.getId())) {
			precio = diasDiferencia > 0 ? (double) (diasDiferencia * VALOR_DIA_MOTO) : (double) (horasDiferencia * VALOR_HORA_MOTO);
			if (estacionamiento.getVehiculo().getCilindraje() > CILINDRAJE_COBRO_ADICIONAL) {
				precio += VALOR_CILINDRAJE_MAYOR_500;
			}
		}
		return precio; 
	}
}
