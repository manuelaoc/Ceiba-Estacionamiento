package com.ceiba.estacionamiento.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
	public List<Estacionamiento> obtenerEstacionamientos(){
		return estacionamientoDAO.obtenerEstacionamientos();
	}
	
	@GetMapping(ESTACIONAMIENTO+"/{placa}")
	public Estacionamiento obtenerEstacionamientoByPlaca(@PathVariable String placa){
		return estacionamientoDAO.obtenerEstacionamientoByPlaca(placa);
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
