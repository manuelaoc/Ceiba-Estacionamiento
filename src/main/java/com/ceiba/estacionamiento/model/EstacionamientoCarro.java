package com.ceiba.estacionamiento.model;

import javax.persistence.Entity;

@Entity
public class EstacionamientoCarro extends Estacionamiento{

	private static final long serialVersionUID = -2754262413450806898L;
	
	public static final Integer MAX_CARROS = 20;
	public static final Integer VALOR_HORA_CARRO = 1000;
	public static final Integer VALOR_DIA_CARRO = 8000;
	
	@Override
	public Boolean validarCantidadVehiculos(Integer cantidadVehiculos) {
		return cantidadVehiculos < MAX_CARROS;
	}
	
	@Override
	public Double calcularPrecio(int horasDiferencia, int diasDiferencia, Integer cilindraje) {
		return diasDiferencia > 0 ? (double) (diasDiferencia * VALOR_DIA_CARRO) : (double) (horasDiferencia * VALOR_HORA_CARRO);
	}
}
