package com.ceiba.estacionamiento.model;

import javax.persistence.Entity;

@Entity
public class EstacionamientoMoto extends Estacionamiento{

	public static final Integer MAX_MOTOS = 10;
	public static final Integer VALOR_HORA_MOTO = 500;
	public static final Integer VALOR_DIA_MOTO = 4000;
	public static final Integer CILINDRAJE_COBRO_ADICIONAL = 500;
	public static final Integer VALOR_CILINDRAJE_MAYOR_500 = 2000;
	
	private static final long serialVersionUID = -1155746463710101749L;
	
	@Override
	public Boolean validarCantidadVehiculos(Integer cantidadVehiculos) {
		return cantidadVehiculos < MAX_MOTOS;
	}
	
	@Override
	public Double calcularPrecio(int horasDiferencia, int diasDiferencia, Integer cilindraje) {
		Double precio;
		if (diasDiferencia > 0) {
			precio = (double) (diasDiferencia * VALOR_DIA_MOTO);
			if (horasDiferencia > 0) {
				precio += (double) (horasDiferencia * VALOR_HORA_MOTO);
			}
		} else {
			precio = (double) (horasDiferencia * VALOR_HORA_MOTO);
		}
		if (cilindraje > CILINDRAJE_COBRO_ADICIONAL) {
			precio += VALOR_CILINDRAJE_MAYOR_500;
		}
		return precio;
	}
}
