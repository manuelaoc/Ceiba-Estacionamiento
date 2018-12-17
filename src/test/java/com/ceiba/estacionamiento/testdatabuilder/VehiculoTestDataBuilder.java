package com.ceiba.estacionamiento.testdatabuilder;

import com.ceiba.estacionamiento.model.TipoVehiculo;
import com.ceiba.estacionamiento.model.Vehiculo;

public class VehiculoTestDataBuilder {
	private static final String PLACA = "APK79C";
	private static final Integer CILINDRAJE = 125;
	private static final TipoVehiculo TIPO_VEHICULO = new TipoVehiculo(2, "Moto");
	
	private String placa;
	private Integer ciclidraje;
	private TipoVehiculo tipoVehiculo;
	
	public VehiculoTestDataBuilder() {
		this.placa = PLACA;
		this.ciclidraje = CILINDRAJE;
		this.tipoVehiculo = TIPO_VEHICULO;
	}
	
	public VehiculoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}
	
	public VehiculoTestDataBuilder conCilindraje(Integer ciclidraje) {
		this.ciclidraje = ciclidraje;
		return this;
	}
	
	public VehiculoTestDataBuilder conTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
		return this;
	}
	
	public Vehiculo build() {
		return new Vehiculo(null, this.placa, this.ciclidraje, this.tipoVehiculo);
	}
}
