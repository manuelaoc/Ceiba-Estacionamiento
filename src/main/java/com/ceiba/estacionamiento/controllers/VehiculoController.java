package com.ceiba.estacionamiento.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.dao.VehiculoDAO;
import com.ceiba.estacionamiento.model.Vehiculo;

@RestController
@RequestMapping("/api")
public class VehiculoController {
	
	private VehiculoDAO vehiculoDAO;
	
	public VehiculoController(VehiculoDAO vehiculoDAO) {
		this.vehiculoDAO = vehiculoDAO;
	}

	@GetMapping("/vehiculos")
	public ResponseEntity<List<Vehiculo>> getVehiculos(){
		List<Vehiculo> vehiculos = vehiculoDAO.getVehiculos();
		
		return new ResponseEntity<>(vehiculos, HttpStatus.OK);
	}

}
