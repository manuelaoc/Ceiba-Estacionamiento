package com.ceiba.estacionamiento.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "estacionamiento")
public class Estacionamiento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@ManyToOne(optional = false)
	private Vehiculo idVehiculo;
	
	@Column(name = "fecha_ingreso")
	private Date fechaIngreso;
	
	@Column(name = "fecha_salida")
	private Date fechaSalida;
	
	@Column(name = "precio")
	private Double precio;
	
	public Estacionamiento() {
		super();
	}

	public Estacionamiento(Integer id, Vehiculo idVehiculo, Date fechaIngreso, Date fechaSalida, Double precio) {
		super();
		this.id = id;
		this.idVehiculo = idVehiculo;
		this.fechaIngreso = fechaIngreso;
		this.fechaSalida = fechaSalida;
		this.precio = precio;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Vehiculo getIdVehiculo() {
		return idVehiculo;
	}

	public void setIdVehiculo(Vehiculo idVehiculo) {
		this.idVehiculo = idVehiculo;
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
