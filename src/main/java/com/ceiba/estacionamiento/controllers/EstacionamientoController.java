package com.ceiba.estacionamiento.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
	public List<Estacionamiento> getEstacionamientos(){
		return estacionamientoDAO.getEstacionamientos();
	}
	
	@GetMapping(ESTACIONAMIENTO+"/{placa}")
	public Estacionamiento getEstacionamientoByPlaca(@PathVariable String placa){
		return estacionamientoDAO.getEstacionamientoByPlaca(placa);
	}
	
	@PostMapping(ESTACIONAMIENTO)
	public void registrarEstacionamiento(@Valid @RequestBody Estacionamiento estacionamiento) {
		if (estacionamientoDAO.validarSiEsPosibleEstacionar(estacionamiento)) {
			estacionamientoDAO.registrarEstacionamiento(estacionamiento);
		}
	}
}
