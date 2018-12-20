package com.ceiba.estacionamiento.service.impl.integracion;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.model.Vehiculo;
import com.ceiba.estacionamiento.service.EstacionamientoService;
import com.ceiba.estacionamiento.service.VehiculoService;
import com.ceiba.estacionamiento.testdatabuilder.EstacionamientoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EstacionamientoServiceImplTest {

	public SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public EstacionamientoDTO estacionamientoEsperado;
	public Vehiculo vehiculo;
	
	@Autowired
	private VehiculoService vehiculoService;
	
	@Autowired
	private EstacionamientoService estacionamientoService;

	@After
	public void after() {
		estacionamientoService.eliminarEstacionamiento(estacionamientoEsperado);
		vehiculoService.eliminarVehiculo(vehiculo);
	}
	
	@Test
	public void generarIngresoEstacionamientoTest() {
		// arrange
		vehiculo = new VehiculoTestDataBuilder().conPlaca("ABC123").build();
		vehiculoService.crearVehiculo(vehiculo);
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso("18/12/2018 09:00:00").build();
		
		// act
		estacionamientoEsperado = estacionamientoService.registrarIngresoEstacionamiento(estacionamientoDTO);
		
		EstacionamientoDTO estacionamientoActual = estacionamientoService.obtenerEstacionamientoByPlaca("ABC123");
		
		// assert
		assertEquals(estacionamientoEsperado.getVehiculo().getPlaca(), estacionamientoActual.getVehiculo().getPlaca());
		assertEquals(estacionamientoEsperado.getVehiculo().getCilindraje(), estacionamientoActual.getVehiculo().getCilindraje());
		assertEquals(estacionamientoEsperado.getVehiculo().getTipoVehiculo(), estacionamientoActual.getVehiculo().getTipoVehiculo());
		assertEquals(format.format(estacionamientoEsperado.getFechaIngreso()), format.format(estacionamientoActual.getFechaIngreso()));
		
	}
	
	@Test
	public void generarSalidaEstacionamientoTest() {
		// arrange
		vehiculo = new VehiculoTestDataBuilder().conPlaca("ABC123").build();
		vehiculoService.crearVehiculo(vehiculo);
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso("18/12/2018 09:00:00").build();
		
		// act
		EstacionamientoDTO estacionamiento = estacionamientoService.registrarIngresoEstacionamiento(estacionamientoDTO);
		estacionamientoEsperado = estacionamientoService.registrarSalidaEstacionamiento(estacionamiento);
		EstacionamientoDTO estacionamientoActual = estacionamientoService.obtenerEstacionamientoByPlaca("ABC123");
		
		// assert
		assertEquals(estacionamientoEsperado.getVehiculo().getPlaca(), estacionamientoActual.getVehiculo().getPlaca());
		assertEquals(estacionamientoEsperado.getVehiculo().getCilindraje(), estacionamientoActual.getVehiculo().getCilindraje());
		assertEquals(estacionamientoEsperado.getVehiculo().getTipoVehiculo(), estacionamientoActual.getVehiculo().getTipoVehiculo());
		assertEquals(format.format(estacionamientoEsperado.getFechaIngreso()), format.format(estacionamientoActual.getFechaIngreso()));
		assertEquals(format.format(estacionamientoEsperado.getFechaSalida()), format.format(estacionamientoActual.getFechaSalida()));
	}
}
