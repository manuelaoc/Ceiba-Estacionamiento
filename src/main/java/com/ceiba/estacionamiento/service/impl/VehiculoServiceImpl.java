package com.ceiba.estacionamiento.service.impl;

import java.util.List;

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
	public List<Vehiculo> obtenerVehiculos() {
		return vehiculoRepository.findAll();
	}

	@Override
	public Vehiculo obtenerVehiculoByPlaca(String placa) {
		return vehiculoRepository.findByPlaca(placa);
	}

	@Override
	public Vehiculo crearVehiculo(Vehiculo vehiculo) {
		return vehiculoRepository.save(vehiculo);
	}

	@Override
	public void eliminarVehiculo(Vehiculo vehiculo) {
		vehiculoRepository.delete(vehiculo);
	}
}
