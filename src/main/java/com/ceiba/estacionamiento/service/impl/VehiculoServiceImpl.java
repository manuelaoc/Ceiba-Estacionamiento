package com.ceiba.estacionamiento.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento.model.Vehiculo;
import com.ceiba.estacionamiento.repository.VehiculoRepository;
import com.ceiba.estacionamiento.service.VehiculoService;

@Service
public class VehiculoServiceImpl implements VehiculoService{

	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Override
	public Vehiculo crearVehiculo(Vehiculo vehiculo) {
		Vehiculo vehiculoExistente = vehiculoRepository.findByPlaca(vehiculo.getPlaca());
		if (vehiculoExistente != null) {
			return vehiculoExistente;
		} else {
			return vehiculoRepository.save(vehiculo);
		}
	}

	@Override
	public void eliminarVehiculo(Vehiculo vehiculo) {
		vehiculoRepository.delete(vehiculo);
	}
}
