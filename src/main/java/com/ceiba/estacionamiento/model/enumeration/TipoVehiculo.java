package com.ceiba.estacionamiento.model.enumeration;

public enum TipoVehiculo {
	CARRO(1, "Carro"),
	MOTO(2, "Moto");
	
	private final Integer id;
	private final String descripcion;
	
	TipoVehiculo(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
}
