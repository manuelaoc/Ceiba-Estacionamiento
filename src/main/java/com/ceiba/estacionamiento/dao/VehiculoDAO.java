package com.ceiba.estacionamiento.dao;

import java.util.List;

import com.ceiba.estacionamiento.model.Vehiculo;

public interface VehiculoDAO {
	List<Vehiculo> getVehiculos();
	Vehiculo getVehiculoByPlaca(String placa);
	Vehiculo crearVehiculo(Vehiculo vehiculo);
}
