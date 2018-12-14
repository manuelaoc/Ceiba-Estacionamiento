package com.ceiba.estacionamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ceiba.estacionamiento.model.Vehiculo;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long>{
	
	Vehiculo findByPlaca(String placa);

}
