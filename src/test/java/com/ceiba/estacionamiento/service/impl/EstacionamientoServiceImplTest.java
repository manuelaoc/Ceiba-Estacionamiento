package com.ceiba.estacionamiento.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import com.ceiba.estacionamiento.controllers.errors.CeibaException;
import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.factory.impl.EstacionamientoFactoryImpl;
import com.ceiba.estacionamiento.model.Estacionamiento;
import com.ceiba.estacionamiento.model.Vehiculo;
import com.ceiba.estacionamiento.model.enumeration.TipoVehiculoEnum;
import com.ceiba.estacionamiento.repository.EstacionamientoRepository;
import com.ceiba.estacionamiento.repository.VehiculoRepository;
import com.ceiba.estacionamiento.testdatabuilder.EstacionamientoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

public class EstacionamientoServiceImplTest {

	private static final String PLACA_VALIDA = "BPK79C";
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private String fechaDiaNoHabil = "17/12/2018";
	private String fechaDiaHabil = "18/12/2018";
	private Integer tipoVehiculoNoPermitido = 3;
	
	@Mock
	EstacionamientoRepository estacionamientoRepository;
	
	@Mock
	VehiculoRepository vehiculoRepository;

	@Spy
	private EstacionamientoFactoryImpl estacionamientoFactoryImpl = new EstacionamientoFactoryImpl();
	
	@InjectMocks
	EstacionamientoServiceImpl estacionamientoServiceImpl;

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void ingresoVehiculoExistenteTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		when(vehiculoRepository.findByPlaca(vehiculo.getPlaca())).thenReturn(vehiculo);
		
		// act
		Boolean existeVehiculo = estacionamientoServiceImpl.validarExistenciaVehiculo(vehiculo.getPlaca());
	
		//assert
		assertTrue(existeVehiculo);
	}
	
	@Test
	public void ingresoVehiculoPermitidoTest() {
		// act
		Boolean existeTipoVehiculo = estacionamientoServiceImpl.validarTipoVehiculo(TipoVehiculoEnum.CARRO.getId());
	
		//assert
		assertTrue(existeTipoVehiculo);
	}
	
	@Test
	public void ingresoVehiculoNoPermitidoTest() {
		// act
		Boolean existeTipoVehiculo = estacionamientoServiceImpl.validarTipoVehiculo(tipoVehiculoNoPermitido);
	
		//assert
		assertFalse(existeTipoVehiculo);
	}

	@Test
	public void permitirIngresoVehiculoConPlacaIniciaATest() throws ParseException {
		// arrange
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		Date fecha = format.parse(fechaDiaHabil);
		
		// act
		Boolean permiteIngreso = estacionamientoServiceImpl.validarIngresoPorPlaca(estacionamiento.getVehiculo().getPlaca(), fecha);
		
		//assert
		assertTrue(permiteIngreso);
	}
	
	@Test
	public void noPermitirIngresoVehiculoConPlacaIniciaATest() throws ParseException {
		// arrange
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		Date fecha = format.parse(fechaDiaNoHabil);
		
		// act
		Boolean permiteIngreso = estacionamientoServiceImpl.validarIngresoPorPlaca(estacionamiento.getVehiculo().getPlaca(), fecha);
		
		//assert
		assertFalse(permiteIngreso);
	}
	
	@Test
	public void validarVehiculoYaEsteEstacionadoTest() {
		// arrange
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		when(estacionamientoServiceImpl.obtenerVehiculoEstacionado(estacionamiento.getVehiculo().getPlaca())).thenReturn(estacionamiento);
				
		// act
		Boolean vehiculoEstacionado = estacionamientoServiceImpl.validarEstaEstacionadoVehiculo(estacionamiento.getVehiculo().getPlaca());
		
		//assert
		assertTrue(vehiculoEstacionado);
	}
	
	@Test
	public void obtenerVehiculoPorPlacaTest() {
		// arrange
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		when(estacionamientoServiceImpl.obtenerEstacionamientoByPlaca(estacionamiento.getVehiculo().getPlaca())).thenReturn(estacionamiento);
				
		// act
		EstacionamientoDTO estacionamientoActual = estacionamientoServiceImpl.obtenerEstacionamientoByPlaca(estacionamiento.getVehiculo().getPlaca());
		
		//assert
		assertEquals(estacionamiento, estacionamientoActual);;
	}
	
	@Test
	public void permitirIngresoVehiculoConPlacaIniciaDiferenteATest() throws ParseException {
		// act
		Boolean permiteIngreso = estacionamientoServiceImpl.validarIngresoPorPlaca(PLACA_VALIDA, new Date());
		
		//assert
		assertTrue(permiteIngreso);
	}
	
	@Test
	public void validarSiEsPosibleEstacionarTipoVehiculoNoExistenteTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipoVehiculo(tipoVehiculoNoPermitido).build();
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).build();
		when(vehiculoRepository.findByPlaca(vehiculo.getPlaca())).thenReturn(vehiculo);
		
		// act
		try {
			estacionamientoServiceImpl.validarSiEsPosibleEstacionar(estacionamiento);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoServiceImpl.TIPO_VEHICULO_NO_PERMITIDO, e.getMessage());
		}
	}
	
	@Test
	public void validarSiEsPosibleEstacionarVehiculoCapacidadMaxTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		when(estacionamientoServiceImpl.contarVehiculosByTipo(TipoVehiculoEnum.MOTO.getId())).thenReturn(20);
		when(vehiculoRepository.findByPlaca(vehiculo.getPlaca())).thenReturn(vehiculo);
		
		// act
		try {
			estacionamientoServiceImpl.validarSiEsPosibleEstacionar(estacionamiento);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoServiceImpl.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
		}
	}
	
	@Test
	public void validarSiEsPosibleEstacionarConPlacaATest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().conFechaIngreso(fechaDiaNoHabil).build();
		when(vehiculoRepository.findByPlaca(vehiculo.getPlaca())).thenReturn(vehiculo);
		
		// act
		try {
			estacionamientoServiceImpl.validarSiEsPosibleEstacionar(estacionamiento);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoServiceImpl.VEHICULO_NO_PUEDE_INGRESAR, e.getMessage());
		}
	}
	
	@Test
	public void validarSiEsPosibleEstacionarVehiculoYaIngresadoTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().build();
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().conFechaIngreso(fechaDiaHabil).build();
		when(estacionamientoServiceImpl.obtenerVehiculoEstacionado(estacionamiento.getVehiculo().getPlaca())).thenReturn(estacionamiento);
		when(vehiculoRepository.findByPlaca(vehiculo.getPlaca())).thenReturn(vehiculo);
		
		// act
		try {
			estacionamientoServiceImpl.validarSiEsPosibleEstacionar(estacionamiento);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoServiceImpl.VEHICULO_YA_ESTA_ESTACIONADO, e.getMessage());
		}
	}
	
	@Test
	public void validarSiEsPosibleEstacionarVehiculoNoExistenteTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca(PLACA_VALIDA).build();
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso(fechaDiaHabil).build();
		when(estacionamientoServiceImpl.validarExistenciaVehiculo(PLACA_VALIDA)).thenReturn(null);
		
		// act
		try {
			estacionamientoServiceImpl.validarSiEsPosibleEstacionar(estacionamiento);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoServiceImpl.VEHICULO_NO_EXISTE, e.getMessage());
		}
	}
	
	@Test
	public void validarSiEsPosibleEstacionarTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca(PLACA_VALIDA).build();
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso(fechaDiaHabil).build();
		when(vehiculoRepository.findByPlaca(PLACA_VALIDA)).thenReturn(vehiculo);
		
		// act
		Estacionamiento estacionamiento = estacionamientoServiceImpl.validarSiEsPosibleEstacionar(estacionamientoDTO);
		
		//assert
		assertEquals(estacionamientoDTO.getVehiculo(), estacionamiento.getVehiculo());
	}
}
