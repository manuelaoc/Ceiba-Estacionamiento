package com.ceiba.estacionamiento.dao;

import java.util.List;

import com.ceiba.estacionamiento.model.Estacionamiento;

public interface EstacionamientoDAO {
	List<Estacionamiento> obtenerEstacionamientos();
	Estacionamiento obtenerEstacionamientoByPlaca(String placa);
	void registrarIngresoEstacionamiento(Estacionamiento estacionamiento);
	Integer contarVehiculosByTipo(Integer idTipoVehiculo);
	Estacionamiento obtenerVehiculoEstacionado(String placa);
	Boolean validarSiEsPosibleEstacionar(Estacionamiento estacionamiento);
	void registrarSalidaEstacionamiento(Estacionamiento estacionamiento);
}
