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
import com.ceiba.estacionamiento.service.EstacionamientoService;

@RestController
@RequestMapping("/api")
public class EstacionamientoController {

	public static final String ESTACIONAMIENTO = "/estacionamiento";
	
	@Autowired
	private EstacionamientoService estacionamientoService;
	
	@GetMapping(ESTACIONAMIENTO)
	public ResponseEntity<List<EstacionamientoDTO>> obtenerEstacionamientos(){
		return new ResponseEntity<>(estacionamientoService.obtenerEstacionamientos(), HttpStatus.OK);
	}
	
	@GetMapping(ESTACIONAMIENTO+"/{placa}")
	public ResponseEntity<EstacionamientoDTO> obtenerEstacionamientoByPlaca(@PathVariable String placa){
		return new ResponseEntity<>(estacionamientoService.obtenerEstacionamientoByPlaca(placa), HttpStatus.OK);
	}
	
	@PostMapping(ESTACIONAMIENTO)
	public void registrarIngresoEstacionamiento(@Valid @RequestBody EstacionamientoDTO estacionamientoDTO) {
		estacionamientoService.registrarIngresoEstacionamiento(estacionamientoDTO);
	}
	
	@PutMapping(ESTACIONAMIENTO)
	public void registrarSalidaEstacionamiento(@Valid @RequestBody EstacionamientoDTO estacionamientoDTO) {
		estacionamientoService.registrarSalidaEstacionamiento(estacionamientoDTO); 
	}
}
