package com.ceiba.estacionamiento.dao;

import java.util.List;

import com.ceiba.estacionamiento.model.Estacionamiento;

public interface EstacionamientoDAO {
	List<Estacionamiento> getEstacionamientos();
	Estacionamiento getEstacionamientoByPlaca(String placa);
	void registrarEstacionamiento(Estacionamiento estacionamiento);
	Integer countVehiculosByTipo(Integer idTipoVehiculo);
	Estacionamiento getVehiculoEstacionado(String placa);
}
