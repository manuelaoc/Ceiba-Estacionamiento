package com.ceiba.estacionamiento.service;

import com.ceiba.estacionamiento.model.Vehiculo;

public interface VehiculoService {
	Vehiculo crearVehiculo(Vehiculo vehiculo);
	void eliminarVehiculo(Vehiculo vehiculo);
}
