package com.ceiba.estacionamiento.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.dao.VehiculoDAO;
import com.ceiba.estacionamiento.model.Vehiculo;

@RestController
@RequestMapping("/api")
public class VehiculoController {
	
	public static final String VEHICULO = "/vehiculo";
	
	@Autowired
	private VehiculoDAO vehiculoDAO;
	
	@GetMapping(VEHICULO)
	public ResponseEntity<List<Vehiculo>> getVehiculos(){
		return new ResponseEntity<>(vehiculoDAO.getVehiculos(), HttpStatus.OK);
	}
	
	@GetMapping(VEHICULO+"/{placa}")
	public ResponseEntity<Vehiculo> getVehiculoByPlaca(@PathVariable String placa){
		return new ResponseEntity<>(vehiculoDAO.getVehiculoByPlaca(placa), HttpStatus.OK);
	}

	@PostMapping(VEHICULO)
	public ResponseEntity<Vehiculo> crearVehiculo(@Valid @RequestBody Vehiculo vehiculo) {
		return new ResponseEntity<>(vehiculoDAO.crearVehiculo(vehiculo), HttpStatus.OK);
	}
}
