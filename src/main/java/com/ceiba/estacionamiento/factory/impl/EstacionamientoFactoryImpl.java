package com.ceiba.estacionamiento.factory.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.factory.EstacionamientoFactory;
import com.ceiba.estacionamiento.model.Estacionamiento;
import com.ceiba.estacionamiento.model.EstacionamientoCarro;
import com.ceiba.estacionamiento.model.EstacionamientoMoto;
import com.ceiba.estacionamiento.model.enumeration.TipoVehiculoEnum;

@Component
public class EstacionamientoFactoryImpl implements EstacionamientoFactory{

	@Override
	public Estacionamiento convertirDTO(EstacionamientoDTO estacionamientoDTO) {
		Estacionamiento estacionamiento = null;
		Integer tipoVehiculo = estacionamientoDTO.getVehiculo().getTipoVehiculo();
		if(tipoVehiculo == TipoVehiculoEnum.CARRO.getId()) {
			estacionamiento = new EstacionamientoCarro();
		}
		if(tipoVehiculo == TipoVehiculoEnum.MOTO.getId()) {
			estacionamiento = new EstacionamientoMoto();
		}
		if (estacionamiento != null) {
			estacionamiento.setId(estacionamientoDTO.getId());
			estacionamiento.setVehiculo(estacionamientoDTO.getVehiculo());
			estacionamiento.setFechaIngreso(estacionamientoDTO.getFechaIngreso());
			estacionamiento.setFechaSalida(estacionamientoDTO.getFechaSalida());
			estacionamiento.setPrecio(estacionamientoDTO.getPrecio());
		}
		return estacionamiento;
	}

	@Override
	public EstacionamientoDTO convertirModelo(Estacionamiento estacionamiento) {
		if (estacionamiento != null) {
			EstacionamientoDTO estacionamientoDTO = new EstacionamientoDTO();
			estacionamientoDTO.setId(estacionamiento.getId());
			estacionamientoDTO.setVehiculo(estacionamiento.getVehiculo());
			estacionamientoDTO.setFechaIngreso(estacionamiento.getFechaIngreso());
			estacionamientoDTO.setFechaSalida(estacionamiento.getFechaSalida());
			estacionamientoDTO.setPrecio(estacionamiento.getPrecio());
			return estacionamientoDTO;
		}
		return null;
	}

	@Override
	public List<EstacionamientoDTO> convertirListaModelo(List<Estacionamiento> estacionamientos) {
		return estacionamientos.stream().map(e -> convertirModelo(e)).collect(Collectors.toList());
	}

}
