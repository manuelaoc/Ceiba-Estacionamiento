package com.ceiba.estacionamiento.service;

import java.util.List;

import com.ceiba.estacionamiento.model.Vehiculo;

public interface VehiculoService {
	List<Vehiculo> obtenerVehiculos();
	Vehiculo obtenerVehiculoByPlaca(String placa);
	Vehiculo crearVehiculo(Vehiculo vehiculo);
	void eliminarVehiculo(Vehiculo vehiculo);
}
