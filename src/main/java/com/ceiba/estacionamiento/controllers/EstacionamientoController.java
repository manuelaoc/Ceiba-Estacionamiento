package com.ceiba.estacionamiento.controllers;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ceiba.estacionamiento.controllers.errors.CeibaException;
import com.ceiba.estacionamiento.dao.EstacionamientoDAO;
import com.ceiba.estacionamiento.model.Estacionamiento;
import com.ceiba.estacionamiento.model.enumeration.TipoVehiculo;

@RestController
@RequestMapping("/api")
public class EstacionamientoController {

	public static final String ESTACIONAMIENTO = "/estacionamiento";
	public static final Integer MAX_CARROS = 20;
	public static final Integer MAX_MOTOS = 10;
	public static final String LETRA_INCIAL = "A";
	
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
		if (validarSiEsPosibleEstacionar(estacionamiento)) {
			estacionamientoDAO.registrarEstacionamiento(estacionamiento);
		}
	}
	
	public Boolean validarSiEsPosibleEstacionar(Estacionamiento estacionamiento) {
		Integer idTipoVehiculo = estacionamiento.getVehiculo().getTipoVehiculo().getId();
		String placa = estacionamiento.getVehiculo().getPlaca();
		if (!validarTipoVehiculo(idTipoVehiculo)) {
			throw new CeibaException("Este vehiculo no puede ingresar al estacionamiento, solo son permitidos carros y motos");
		}
		if (!validarCantidadVehiculos(idTipoVehiculo)) {
			throw new CeibaException("Ya no hay mas cupos disponibles para estacionar este tipo de vehiculo");
		}
		if (!validarIngresoPorPlaca(placa)) {
			throw new CeibaException("Este vehiculo no puede ingresar dado que no esta en un dia habil");
		}
		if (validarSiEstaElVehiculoEstacionado(placa)) {
			throw new CeibaException("El vehiculo con placa " + placa + " ya se encuentra dentro del estacionamiento");
		}
		return true;
	}
	
	public Boolean validarTipoVehiculo(Integer idTipoVehiculo) {
		return TipoVehiculo.getTipoVehiculos().stream().anyMatch(t -> t.getId().equals(idTipoVehiculo));
	}
	
	public Boolean validarCantidadVehiculos(Integer idTipoVehiculo) {
		Integer countVehiculos = estacionamientoDAO.countVehiculosByTipo(idTipoVehiculo);
		if (idTipoVehiculo.equals(TipoVehiculo.CARRO.getId())) {
			return countVehiculos < MAX_CARROS;
		} else if (idTipoVehiculo.equals(TipoVehiculo.MOTO.getId())) {
			return countVehiculos < MAX_MOTOS;
		}
		return false;
	}
	
	public Boolean validarIngresoPorPlaca(String placa) {
		if (placa.startsWith(LETRA_INCIAL)) {
			return permitirIngresoVehiculoConPlacaA();
		}
		return true;
	}
	
	public Boolean permitirIngresoVehiculoConPlacaA() {
		Calendar date = Calendar.getInstance();
		Integer diaActual = date.get(Calendar.DAY_OF_WEEK);
		return diaActual != 1 || diaActual != 2;
	}
	
	public Boolean validarSiEstaElVehiculoEstacionado(String placa) {
		return estacionamientoDAO.getVehiculoEstacionado(placa) != null;
	}
}
