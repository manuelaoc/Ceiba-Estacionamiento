package com.ceiba.estacionamiento.service;

import java.util.Date;
import java.util.List;

import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.model.Estacionamiento;

public interface EstacionamientoService {
	List<EstacionamientoDTO> obtenerEstacionamientos();
	EstacionamientoDTO obtenerEstacionamientoByPlaca(String placa);
	EstacionamientoDTO registrarIngresoEstacionamiento(EstacionamientoDTO estacionamientoDTO);
	Integer contarVehiculosByTipo(Integer idTipoVehiculo);
	EstacionamientoDTO registrarSalidaEstacionamiento(EstacionamientoDTO estacionamientoDTO);
	void eliminarEstacionamiento(EstacionamientoDTO estacionamientoDTO);
	Estacionamiento validarSiEsPosibleEstacionar(EstacionamientoDTO estacionamientoDTO);
	Boolean validarExistenciaVehiculo(String placa);
	Boolean validarTipoVehiculo(Integer idTipoVehiculo);
	Boolean validarIngresoPorPlaca(String placa, Date fecha);
	Boolean validarEstaEstacionadoVehiculo(String placa);
	EstacionamientoDTO obtenerVehiculoEstacionado(String placa);
	Double generarReciboSalida(EstacionamientoDTO estacionamientoDTO, Date fechaSalida);
}
