package com.ceiba.estacionamiento.controllers.errors;

public class CeibaException extends RuntimeException{

	private static final long serialVersionUID = 5254386163205972675L;

	public CeibaException(String mensaje) {
		super(mensaje);
	}
}
