package com.ceiba.estacionamiento.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.dao.EstacionamientoDAO;
import com.ceiba.estacionamiento.model.Estacionamiento;

@RestController
@RequestMapping("/api")
public class EstacionamientoController {

	public static final String ESTACIONAMIENTO = "/estacionamiento";
	
	@Autowired
	private EstacionamientoDAO estacionamientoDAO;
	
	@GetMapping(ESTACIONAMIENTO)
	public ResponseEntity<List<Estacionamiento>> obtenerEstacionamientos(){
		return new ResponseEntity<>(estacionamientoDAO.obtenerEstacionamientos(), HttpStatus.OK);
	}
	
	@GetMapping(ESTACIONAMIENTO+"/{placa}")
	public ResponseEntity<Estacionamiento> obtenerEstacionamientoByPlaca(@PathVariable String placa){
		return new ResponseEntity<>(estacionamientoDAO.obtenerEstacionamientoByPlaca(placa), HttpStatus.OK);
	}
	
	@PostMapping(ESTACIONAMIENTO)
	public void registrarIngresoEstacionamiento(@Valid @RequestBody Estacionamiento estacionamiento) {
		estacionamientoDAO.registrarIngresoEstacionamiento(estacionamiento);
	}
	
	@PutMapping(ESTACIONAMIENTO)
	public void registrarSalidaEstacionamiento(@Valid @RequestBody Estacionamiento estacionamiento) {
		estacionamientoDAO.registrarSalidaEstacionamiento(estacionamiento); 
	}
}
