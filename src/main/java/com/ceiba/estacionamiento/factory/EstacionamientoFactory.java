package com.ceiba.estacionamiento.factory;

import java.util.List;

import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.model.Estacionamiento;

public interface EstacionamientoFactory {
	
	public Estacionamiento convertirDTO(EstacionamientoDTO estacionamientoDTO);
	public EstacionamientoDTO convertirModelo(Estacionamiento estacionamiento);
	public List<EstacionamientoDTO> convertirListaModelo(List<Estacionamiento> estacionamientos);
}
