package com.ceiba.estacionamiento.service;

import java.util.List;

import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.model.Estacionamiento;

public interface EstacionamientoService {
	List<Estacionamiento> obtenerEstacionamientos();
	Estacionamiento obtenerEstacionamientoByPlaca(String placa);
	void registrarIngresoEstacionamiento(EstacionamientoDTO estacionamientoDTO);
	Integer contarVehiculosByTipo(Integer idTipoVehiculo);
	Estacionamiento obtenerVehiculoEstacionado(String placa);
	void registrarSalidaEstacionamiento(EstacionamientoDTO estacionamientoDTO);
}
