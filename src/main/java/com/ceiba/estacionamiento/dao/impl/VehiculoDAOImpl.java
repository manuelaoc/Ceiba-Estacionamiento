package com.ceiba.estacionamiento.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento.dao.VehiculoDAO;
import com.ceiba.estacionamiento.model.Vehiculo;
import com.ceiba.estacionamiento.repository.VehiculoRepository;

@Service
public class VehiculoDAOImpl implements VehiculoDAO{

	@Autowired
	private VehiculoRepository vehiculoRepository;
	
	@Override
	public List<Vehiculo> getVehiculos() {
		return vehiculoRepository.findAll();
	}

	@Override
	public Vehiculo getVehiculoByPlaca(String placa) {
		return vehiculoRepository.findByPlaca(placa);
	}

	@Override
	public void crearVehiculo(Vehiculo vehiculo) {
		vehiculoRepository.save(vehiculo);
	}
}
