package com.ceiba.estacionamiento.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento.model.Vehiculo;

public interface VehiculoDAO {
	List<Vehiculo> getVehiculos();
	Vehiculo getVehiculoByPlaca(String placa);
	void crearVehiculo(Vehiculo vehiculo);
}
