package com.ceiba.estacionamiento.dto;

import java.io.Serializable;
import java.util.Date;

import com.ceiba.estacionamiento.model.Vehiculo;

public class EstacionamientoDTO implements Serializable{

	private static final long serialVersionUID = -5518710865853802303L;
	
	private Integer id;
	private Vehiculo vehiculo;
	private Date fechaIngreso;
	private Date fechaSalida;
	private Double precio;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Vehiculo getVehiculo() {
		return vehiculo;
	}
	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	public Date getFechaIngreso() {
		return fechaIngreso;
	}
	public void setFechaIngreso(Date fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public Double getPrecio() {
		return precio;
	}
	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	
}
