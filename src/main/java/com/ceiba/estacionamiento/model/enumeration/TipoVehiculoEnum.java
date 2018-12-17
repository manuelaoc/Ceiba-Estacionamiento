package com.ceiba.estacionamiento.model.enumeration;

import java.util.Arrays;
import java.util.List;

public enum TipoVehiculoEnum {
	CARRO(1, "Carro"),
	MOTO(2, "Moto");
	
	private final Integer id;
	private final String descripcion;
	
	TipoVehiculoEnum(Integer id, String descripcion) {
		this.id = id;
		this.descripcion = descripcion;
	}

	public Integer getId() {
		return id;
	}

	public String getDescripcion() {
		return descripcion;
	}
	
	public static List<TipoVehiculoEnum> obtenerTipoVehiculos(){
		return Arrays.asList(TipoVehiculoEnum.values());
	}
} 
