package com.ceiba.estacionamiento.testdatabuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.model.Vehiculo;

public class EstacionamientoTestDataBuilder {
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
	private static final Vehiculo VEHICULO = new Vehiculo(100, "APK79C", 125, 2);
	private static final Date FECHA_INGRESO = new Date();
	private static final Date FECHA_SALIDA = null;
	private static final Double PRECIO = null;
	
	private Vehiculo vehiculo;
	private Date fechaIngreso;
	private Date fechaSalida;
	private Double precio;
	
	public EstacionamientoTestDataBuilder() {
		this.vehiculo = VEHICULO;
		this.fechaIngreso = FECHA_INGRESO;
		this.fechaSalida = FECHA_SALIDA;
		this.precio = PRECIO;
	}
	
	public EstacionamientoTestDataBuilder conVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
		return this;
	}
	
	public EstacionamientoTestDataBuilder conFechaIngreso(String fechaIngreso) {
		try {
			this.fechaIngreso = format.parse(fechaIngreso);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return this;
	}
	
	public EstacionamientoTestDataBuilder conFechaSalida(String fechaSalida) {
		try {
			this.fechaSalida = format.parse(fechaSalida);
		} catch (ParseException e) {
			this.fechaSalida = null;
		}
		return this;
	}
	
	public EstacionamientoTestDataBuilder conPrecio(Double precio) {
		this.precio = precio;
		return this;
	}
	
	public EstacionamientoDTO build() {
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoDTO();
		estacionamientoDTO.setVehiculo(this.vehiculo);
		estacionamientoDTO.setFechaIngreso(this.fechaIngreso);
		estacionamientoDTO.setFechaSalida(this.fechaSalida);
		estacionamientoDTO.setPrecio(this.precio);
		return estacionamientoDTO;
	}
}
