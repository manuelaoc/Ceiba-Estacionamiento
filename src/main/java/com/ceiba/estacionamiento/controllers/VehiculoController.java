package com.ceiba.estacionamiento.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.model.Vehiculo;
import com.ceiba.estacionamiento.service.VehiculoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class VehiculoController {

	public static final String VEHICULO = "/vehiculo";
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@PostMapping(VEHICULO)
	public ResponseEntity<Vehiculo> guardarVehiculo(@Valid @RequestBody Vehiculo vehiculo) {
		return new ResponseEntity<>(vehiculoService.crearVehiculo(vehiculo), HttpStatus.CREATED);
	}
}
