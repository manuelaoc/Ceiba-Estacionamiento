package com.ceiba.estacionamiento.dao.impl;

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
import org.mockito.Mockito;

import com.ceiba.estacionamiento.controllers.errors.CeibaException;
import com.ceiba.estacionamiento.model.Estacionamiento;
import com.ceiba.estacionamiento.model.TipoVehiculo;
import com.ceiba.estacionamiento.model.Vehiculo;
import com.ceiba.estacionamiento.model.enumeration.TipoVehiculoEnum;
import com.ceiba.estacionamiento.repository.EstacionamientoRepository;
import com.ceiba.estacionamiento.testdatabuilder.EstacionamientoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

public class EstacionamientoDAOImplTest {

	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
	private String fechaDiaLunes = "17/12/2018";
	private Integer tipoVehiculoNoPermitido = 3;
	
	@Mock
	EstacionamientoRepository estacionamientoRepository;

	@InjectMocks
	EstacionamientoDAOImpl estacionamientoDAOImpl;

	@Before
	public void before() {
		estacionamientoRepository = Mockito.mock(EstacionamientoRepository.class);
		estacionamientoDAOImpl = new EstacionamientoDAOImpl(estacionamientoRepository);
	}
	
	@Test
	public void ingresoVehiculoPermitidoTest() {
		// act
		Boolean existeTipoVehiculo = estacionamientoDAOImpl.validarTipoVehiculo(TipoVehiculoEnum.CARRO.getId());
	
		//assert
		assertTrue(existeTipoVehiculo);
	}
	
	@Test
	public void ingresoVehiculoNoPermitidoTest() {
		// act
		Boolean existeTipoVehiculo = estacionamientoDAOImpl.validarTipoVehiculo(tipoVehiculoNoPermitido);
	
		//assert
		assertFalse(existeTipoVehiculo);
	}

	@Test
	public void estacionamientoTieneCuposCarroTest() {
		// arrange
		when(estacionamientoDAOImpl.contarVehiculosByTipo(TipoVehiculoEnum.CARRO.getId())).thenReturn(0);
		
		// act
		Boolean tieneCupos = estacionamientoDAOImpl.validarCantidadVehiculos(TipoVehiculoEnum.CARRO.getId());
		
		//assert
		assertTrue(tieneCupos);
		
	}
	
	@Test
	public void estacionamientoNoTieneCuposCarroTest() {
		// arrange
		when(estacionamientoDAOImpl.contarVehiculosByTipo(TipoVehiculoEnum.CARRO.getId())).thenReturn(21);
		
		// act
		Boolean tieneCupos = estacionamientoDAOImpl.validarCantidadVehiculos(TipoVehiculoEnum.CARRO.getId());
		
		//assert
		assertFalse(tieneCupos);
		
	}
	
	@Test
	public void estacionamientoTieneCuposMotoTest() {
		// arrange
		when(estacionamientoDAOImpl.contarVehiculosByTipo(TipoVehiculoEnum.MOTO.getId())).thenReturn(0);
		
		// act
		Boolean tieneCupos = estacionamientoDAOImpl.validarCantidadVehiculos(TipoVehiculoEnum.MOTO.getId());
		
		//assert
		assertTrue(tieneCupos);
	}
	
	@Test
	public void estacionamientoNoTieneCuposMotoTest() {
		// arrange
		when(estacionamientoDAOImpl.contarVehiculosByTipo(TipoVehiculoEnum.MOTO.getId())).thenReturn(21);
		
		// act
		Boolean tieneCupos = estacionamientoDAOImpl.validarCantidadVehiculos(TipoVehiculoEnum.MOTO.getId());
		
		//assert
		assertFalse(tieneCupos);
	}
	
	@Test
	public void estacionamientoTieneCuposTipoVehiculoNoPermitidoTest() {
		// act
		try {
			estacionamientoDAOImpl.validarCantidadVehiculos(tipoVehiculoNoPermitido);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoDAOImpl.TIPO_VEHICULO_NO_PERMITIDO, e.getMessage());
		}
	}
	
	@Test
	public void permitirIngresoVehiculoConPlacaIniciaATest() throws ParseException {
		// arrange
		Estacionamiento estacionamiento = new EstacionamientoTestDataBuilder().build();
		Date fecha = format.parse("18/12/2018");
		
		// act
		Boolean permiteIngreso = estacionamientoDAOImpl.validarIngresoPorPlaca(estacionamiento.getVehiculo().getPlaca(), fecha);
		
		//assert
		assertTrue(permiteIngreso);
	}
	
	@Test
	public void noPermitirIngresoVehiculoConPlacaIniciaATest() throws ParseException {
		// arrange
		Estacionamiento estacionamiento = new EstacionamientoTestDataBuilder().build();
		Date fecha = format.parse(fechaDiaLunes);
		
		// act
		Boolean permiteIngreso = estacionamientoDAOImpl.validarIngresoPorPlaca(estacionamiento.getVehiculo().getPlaca(), fecha);
		
		//assert
		assertFalse(permiteIngreso);
	}
	
	@Test
	public void validarVehiculoYaEsteEstacionadoTest() {
		// arrange
		Estacionamiento estacionamiento = new EstacionamientoTestDataBuilder().build();
		when(estacionamientoDAOImpl.obtenerVehiculoEstacionado(estacionamiento.getVehiculo().getPlaca())).thenReturn(estacionamiento);
				
		// act
		Boolean vehiculoEstacionado = estacionamientoDAOImpl.validarEstaEstacionadoVehiculo(estacionamiento.getVehiculo().getPlaca());
		
		//assert
		assertTrue(vehiculoEstacionado);
	}
	
	@Test
	public void obtenerVehiculoPorPlacaTest() {
		// arrange
		Estacionamiento estacionamiento = new EstacionamientoTestDataBuilder().build();
		when(estacionamientoDAOImpl.obtenerEstacionamientoByPlaca(estacionamiento.getVehiculo().getPlaca())).thenReturn(estacionamiento);
				
		// act
		Estacionamiento estacionamientoActual = estacionamientoDAOImpl.obtenerEstacionamientoByPlaca(estacionamiento.getVehiculo().getPlaca());
		
		//assert
		assertEquals(estacionamiento, estacionamientoActual);;
	}
	
	@Test
	public void permitirIngresoVehiculoConPlacaIniciaDiferenteATest() throws ParseException {
		// arrange
		String placa = "BPK79C";
		
		// act
		Boolean permiteIngreso = estacionamientoDAOImpl.validarIngresoPorPlaca(placa, new Date());
		
		//assert
		assertTrue(permiteIngreso);
	}
	
	@Test
	public void validarSiEsPosibleEstacionarTipoVehiculoNoExistenteTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipoVehiculo(new TipoVehiculo(tipoVehiculoNoPermitido, "Camion")).build();
		Estacionamiento estacionamiento = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).build();
		
		// act
		try {
			estacionamientoDAOImpl.validarSiEsPosibleEstacionar(estacionamiento);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoDAOImpl.TIPO_VEHICULO_NO_PERMITIDO, e.getMessage());
		}
	}
	
	@Test
	public void validarSiEsPosibleEstacionarCantidadEstacionadaMaxTest() {
		// arrange
		Estacionamiento estacionamiento = new EstacionamientoTestDataBuilder().build();
		when(estacionamientoDAOImpl.contarVehiculosByTipo(TipoVehiculoEnum.MOTO.getId())).thenReturn(20);
		
		// act
		try {
			estacionamientoDAOImpl.validarSiEsPosibleEstacionar(estacionamiento);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoDAOImpl.NO_HAY_CUPOS_DISPONIBLES, e.getMessage());
		}
	}
	
	@Test
	public void validarSiEsPosibleEstacionarConPlacaATest() {
		// arrange
		Estacionamiento estacionamiento = new EstacionamientoTestDataBuilder().conFechaIngreso(fechaDiaLunes).build();
		
		// act
		try {
			estacionamientoDAOImpl.validarSiEsPosibleEstacionar(estacionamiento);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoDAOImpl.VEHICULO_NO_PUEDE_INGRESAR, e.getMessage());
		}
	}
	
	@Test
	public void validarSiEsPosibleEstacionarVehiculoYaIngresadoTest() {
		// arrange
		Estacionamiento estacionamiento = new EstacionamientoTestDataBuilder().conFechaIngreso("18/12/2018").build();
		when(estacionamientoDAOImpl.obtenerVehiculoEstacionado(estacionamiento.getVehiculo().getPlaca())).thenReturn(estacionamiento);
		
		// act
		try {
			estacionamientoDAOImpl.validarSiEsPosibleEstacionar(estacionamiento);
		}catch (CeibaException e) {
			//assert
			assertEquals(EstacionamientoDAOImpl.VEHICULO_YA_ESTA_ESTACIONADO, e.getMessage());
		}
	}
	
	@Test
	public void validarSiEsPosibleEstacionarTest() {
		// arrange
		Estacionamiento estacionamiento = new EstacionamientoTestDataBuilder().conFechaIngreso("18/12/2018").build();
		
		// act
		Boolean esPosible = estacionamientoDAOImpl.validarSiEsPosibleEstacionar(estacionamiento);
		
		//assert
		assertTrue(esPosible);
	}
}
