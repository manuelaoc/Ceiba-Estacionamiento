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

import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.model.Estacionamiento;
import com.ceiba.estacionamiento.service.EstacionamientoService;

@RestController
@RequestMapping("/api")
public class EstacionamientoController {

	public static final String ESTACIONAMIENTO = "/estacionamiento";
	
	@Autowired
	private EstacionamientoService estacionamientoDAO;
	
	@GetMapping(ESTACIONAMIENTO)
	public ResponseEntity<List<Estacionamiento>> obtenerEstacionamientos(){
		return new ResponseEntity<>(estacionamientoDAO.obtenerEstacionamientos(), HttpStatus.OK);
	}
	
	@GetMapping(ESTACIONAMIENTO+"/{placa}")
	public ResponseEntity<Estacionamiento> obtenerEstacionamientoByPlaca(@PathVariable String placa){
		return new ResponseEntity<>(estacionamientoDAO.obtenerEstacionamientoByPlaca(placa), HttpStatus.OK);
	}
	
	@PostMapping(ESTACIONAMIENTO)
	public void registrarIngresoEstacionamiento(@Valid @RequestBody EstacionamientoDTO estacionamientoDTO) {
		estacionamientoDAO.registrarIngresoEstacionamiento(estacionamientoDTO);
	}
	
	@PutMapping(ESTACIONAMIENTO)
	public void registrarSalidaEstacionamiento(@Valid @RequestBody EstacionamientoDTO estacionamientoDTO) {
		estacionamientoDAO.registrarSalidaEstacionamiento(estacionamientoDTO); 
	}
}
