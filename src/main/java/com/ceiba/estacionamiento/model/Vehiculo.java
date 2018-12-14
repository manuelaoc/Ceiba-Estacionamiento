package com.ceiba.estacionamiento.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "vehiculo")
public class Vehiculo implements Serializable {
	
	private static final long serialVersionUID = -3373131848146066197L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	@NotNull
	@Column(name = "placa")
	private String placa;
	
	@NotNull
	@Column(name = "cilindraje")
	private Integer cilindraje;
	
	@NotNull
	@ManyToOne(optional = false)
	private TipoVehiculo tipoVehiculo;
	
	public Vehiculo(Integer id, String placa, Integer cilindraje, TipoVehiculo tipoVehiculo) {
		super();
		this.id = id;
		this.placa = placa;
		this.cilindraje = cilindraje;
		this.tipoVehiculo = tipoVehiculo;
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

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
}
