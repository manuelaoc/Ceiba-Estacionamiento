package com.ceiba.estacionamiento.factory;

import java.util.List;

import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.model.Estacionamiento;

public interface EstacionamientoFactory {
	
	public Estacionamiento convertirDTOaModelo(EstacionamientoDTO estacionamientoDTO);
	public EstacionamientoDTO convertirModeloaDTO(Estacionamiento estacionamiento);
	public List<EstacionamientoDTO> convertirListaModelo(List<Estacionamiento> estacionamientos);
}
