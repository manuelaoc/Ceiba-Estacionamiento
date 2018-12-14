package com.ceiba.estacionamiento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ceiba.estacionamiento.model.Estacionamiento;

@Repository
public interface EstacionamientoRepository extends JpaRepository<Estacionamiento, Long> {
	
	@Query("SELECT est FROM Estacionamiento est WHERE est.vehiculo.placa = (:placa)")
	Estacionamiento findEstacionamientoByPlaca(@Param("placa") String placa);
	
	@Query("SELECT COUNT(est) FROM Estacionamiento est WHERE est.fechaSalida IS NULL AND est.precio IS NULL AND est.vehiculo.tipoVehiculo.id = (:idTipoVehiculo)")
	Integer countVehiculosByTipo(@Param("idTipoVehiculo") Integer idTipoVehiculo);
	
	@Query("SELECT est FROM Estacionamiento est WHERE est.vehiculo.placa = (:placa) AND est.fechaSalida IS NULL AND est.precio IS NULL")
	Estacionamiento getVehiculoEstacionado(@Param("placa") String placa);
}
