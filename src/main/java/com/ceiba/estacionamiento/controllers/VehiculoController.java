package com.ceiba.estacionamiento.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.model.Vehiculo;
import com.ceiba.estacionamiento.service.VehiculoService;

@RestController
@RequestMapping("/api")
public class VehiculoController {
	
	public static final String VEHICULO = "/vehiculo";
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@GetMapping(VEHICULO)
	public ResponseEntity<List<Vehiculo>> obtenerVehiculos(){
		return new ResponseEntity<>(vehiculoService.obtenerVehiculos(), HttpStatus.OK);
	}
	
	@GetMapping(VEHICULO+"/{placa}")
	public ResponseEntity<Vehiculo> obtenerVehiculoByPlaca(@PathVariable String placa){
		return new ResponseEntity<>(vehiculoService.obtenerVehiculoByPlaca(placa), HttpStatus.OK);
	}

	@PostMapping(VEHICULO)
	public ResponseEntity<Vehiculo> crearVehiculo(@RequestBody Vehiculo vehiculo) {
		return new ResponseEntity<>(vehiculoService.crearVehiculo(vehiculo), HttpStatus.OK);
	}
}
