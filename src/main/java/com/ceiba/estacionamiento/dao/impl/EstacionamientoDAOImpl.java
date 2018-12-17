package com.ceiba.estacionamiento.dao.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
	
	@Autowired
	private EstacionamientoRepository estacionamientoRepository; 
	
	@Autowired
	public EstacionamientoDAOImpl(EstacionamientoRepository estacionamientoRepository){
	  this.estacionamientoRepository = estacionamientoRepository;
	}
	
	@Override
	public void registrarEstacionamiento(Estacionamiento estacionamiento) {
		estacionamientoRepository.save(estacionamiento);
	}

	@Override
	public List<Estacionamiento> getEstacionamientos() {
		return estacionamientoRepository.findAll();
	}

	@Override
	public Estacionamiento getEstacionamientoByPlaca(String placa) {
		return estacionamientoRepository.findEstacionamientoByPlaca(placa);
	}

	@Override
	public Integer countVehiculosByTipo(Integer idTipoVehiculo) {
		return estacionamientoRepository.countVehiculosByTipo(idTipoVehiculo);
	}

	@Override
	public Estacionamiento getVehiculoEstacionado(String placa) {
		return estacionamientoRepository.getVehiculoEstacionado(placa);
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
		return TipoVehiculoEnum.getTipoVehiculos().stream().anyMatch(t -> t.getId().equals(idTipoVehiculo));
	}
	
	public Boolean validarCantidadVehiculos(Integer idTipoVehiculo) {
		Integer countVehiculos = countVehiculosByTipo(idTipoVehiculo);
		if (idTipoVehiculo.equals(TipoVehiculoEnum.CARRO.getId())) {
			return countVehiculos < MAX_CARROS;
		} else if (idTipoVehiculo.equals(TipoVehiculoEnum.MOTO.getId())) {
			return countVehiculos < MAX_MOTOS;
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
		return getVehiculoEstacionado(placa) != null;
	}
}
