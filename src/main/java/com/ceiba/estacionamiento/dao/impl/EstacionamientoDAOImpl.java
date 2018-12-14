package com.ceiba.estacionamiento.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ceiba.estacionamiento.dao.EstacionamientoDAO;
import com.ceiba.estacionamiento.model.Estacionamiento;
import com.ceiba.estacionamiento.repository.EstacionamientoRepository;

@Service
public class EstacionamientoDAOImpl implements EstacionamientoDAO{

	@Autowired
	private EstacionamientoRepository estacionamientoRepository; 
	
	@Override
	public void registrarEstacionamiento(Estacionamiento estacionamiento) {
		estacionamientoRepository.save(estacionamiento);
	}

	@Override
	public List<Estacionamiento> getEstacionamientos() {
		return estacionamientoRepository.findAll();
	}

	@Override
	public Estacionamiento getEstacionamientoByPlaca(String placa) {
		return estacionamientoRepository.findEstacionamientoByPlaca(placa);
	}

	@Override
	public Integer countVehiculosByTipo(Integer idTipoVehiculo) {
		return estacionamientoRepository.countVehiculosByTipo(idTipoVehiculo);
	}

	@Override
	public Estacionamiento getVehiculoEstacionado(String placa) {
		return estacionamientoRepository.getVehiculoEstacionado(placa);
	}
}
