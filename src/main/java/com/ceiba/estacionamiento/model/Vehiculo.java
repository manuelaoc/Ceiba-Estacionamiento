package com.ceiba.estacionamiento.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "vehiculo")
public class Vehiculo {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@Column(name = "placa")
	private String placa;
	
	@Column(name = "cilindraje")
	private Integer cilindraje;
	
	@ManyToOne(optional = false)
	private TipoVehiculo idTipoVehiculo;
	
	public Vehiculo() {
		super();
	}

	public Vehiculo(Integer id, String placa, Integer cilindraje, TipoVehiculo idTipoVehiculo) {
		super();
		this.id = id;
		this.placa = placa;
		this.cilindraje = cilindraje;
		this.idTipoVehiculo = idTipoVehiculo;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public Integer getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}

	public TipoVehiculo getIdTipoVehiculo() {
		return idTipoVehiculo;
	}

	public void setIdTipoVehiculo(TipoVehiculo idTipoVehiculo) {
		this.idTipoVehiculo = idTipoVehiculo;
	}
}
