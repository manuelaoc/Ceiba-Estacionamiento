package com.ceiba.estacionamiento.util;

public class TiempoEstacionamiento {

	private int horasDiferencia;
	private int diasDiferencia;
	
	public TiempoEstacionamiento(int horasDiferencia, int diasDiferencia) {
		super();
		this.horasDiferencia = horasDiferencia;
		this.diasDiferencia = diasDiferencia;
	}

	public int getHorasDiferencia() {
		return horasDiferencia;
	}

	public int getDiasDiferencia() {
		return diasDiferencia;
	}

}
