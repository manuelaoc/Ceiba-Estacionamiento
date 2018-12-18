package com.ceiba.estacionamiento.factory;

import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.model.Estacionamiento;

public interface EstacionamientoFactory {
	
	public Estacionamiento convertirDTO(EstacionamientoDTO estacionamientoDTO);
	public EstacionamientoDTO convertirModelo(Estacionamiento estacionamiento);
	
}
