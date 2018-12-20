package com.ceiba.estacionamiento.service.impl.unitarias;

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
import com.ceiba.estacionamiento.service.impl.EstacionamientoServiceImpl;
import com.ceiba.estacionamiento.testdatabuilder.EstacionamientoTestDataBuilder;
import com.ceiba.estacionamiento.testdatabuilder.VehiculoTestDataBuilder;

public class EstacionamientoServiceImplTest {

	private static final Integer TIPO_VEHICULO_NO_PERMITIDO = 3;
	private static final String PLACA_VALIDA_MOTO = "BPK79C";
	private static final String PLACA_VALIDA_CARRO = "WBE068";
	private static final String FECHA_DIA_HABIL = "18/12/2018 09:00:00";
	private static final String FECHA_DIA_NO_HABIL = "17/12/2018 13:00:00";
	private static final String FECHA_SALIDA_CARRO = "19/12/2018 12:00:00";
	private static final String FECHA_SALIDA_MOTO = "18/12/2018 19:00:00";
	
	private DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
	
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
		Boolean existeTipoVehiculo = estacionamientoServiceImpl.validarTipoVehiculo(TIPO_VEHICULO_NO_PERMITIDO);
	
		//assert
		assertFalse(existeTipoVehiculo);
	}

	@Test
	public void permitirIngresoVehiculoConPlacaIniciaATest() throws ParseException {
		// arrange
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		Date fecha = format.parse(FECHA_DIA_HABIL);
		
		// act
		Boolean permiteIngreso = estacionamientoServiceImpl.validarIngresoPorPlaca(estacionamiento.getVehiculo().getPlaca(), fecha);
		
		//assert
		assertTrue(permiteIngreso);
	}
	
	@Test
	public void noPermitirIngresoVehiculoConPlacaIniciaATest() throws ParseException {
		// arrange
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		Date fecha = format.parse(FECHA_DIA_NO_HABIL);
		
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
		Boolean permiteIngreso = estacionamientoServiceImpl.validarIngresoPorPlaca(PLACA_VALIDA_MOTO, new Date());
		
		//assert
		assertTrue(permiteIngreso);
	}
	
	@Test
	public void validarSiEsPosibleEstacionarTipoVehiculoNoExistenteTest() {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conTipoVehiculo(TIPO_VEHICULO_NO_PERMITIDO).build();
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
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().conFechaIngreso(FECHA_DIA_NO_HABIL).build();
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
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().conFechaIngreso(FECHA_DIA_HABIL).build();
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
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca(PLACA_VALIDA_MOTO).build();
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso(FECHA_DIA_HABIL).build();
		when(estacionamientoServiceImpl.validarExistenciaVehiculo(PLACA_VALIDA_MOTO)).thenReturn(null);
		
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
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca(PLACA_VALIDA_MOTO).build();
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso(FECHA_DIA_HABIL).build();
		when(vehiculoRepository.findByPlaca(PLACA_VALIDA_MOTO)).thenReturn(vehiculo);
		
		// act
		Estacionamiento estacionamiento = estacionamientoServiceImpl.validarSiEsPosibleEstacionar(estacionamientoDTO);
		
		// assert
		assertEquals(estacionamientoDTO.getVehiculo(), estacionamiento.getVehiculo());
	}
	
	@Test
	public void validarGeneracionReciboSalidaCarroTest() throws ParseException {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca(PLACA_VALIDA_CARRO).conTipoVehiculo(TipoVehiculoEnum.CARRO.getId()).build();
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso(FECHA_DIA_HABIL).build();
		
		// act
		Double precio = estacionamientoServiceImpl.generarReciboSalida(estacionamientoDTO, format.parse(FECHA_SALIDA_CARRO));
		
		// assert
		assertEquals(11000, precio, 0);
	}
	
	@Test
	public void validarGeneracionReciboSalidaMotoCilindrajeMenos500Test() throws ParseException {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca(PLACA_VALIDA_MOTO).build();
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso(FECHA_DIA_HABIL).build();
		
		// act
		Double precio = estacionamientoServiceImpl.generarReciboSalida(estacionamientoDTO, format.parse(FECHA_SALIDA_MOTO));
		
		// assert
		assertEquals(4000, precio, 0);
	}
	
	@Test
	public void validarGeneracionReciboSalidaMotoCilindrajeMayor500Test() throws ParseException {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca(PLACA_VALIDA_MOTO).conCilindraje(650).build();
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso(FECHA_DIA_HABIL).build();
		
		// act
		Double precio = estacionamientoServiceImpl.generarReciboSalida(estacionamientoDTO, format.parse(FECHA_SALIDA_MOTO));
		
		// assert
		assertEquals(6000, precio, 0);
	}

	@Test
	public void validarGeneracionReciboSalidaMotoTiempoEstacionadoMinutosTest() throws ParseException {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca(PLACA_VALIDA_MOTO).build();
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso(FECHA_DIA_HABIL).build();
		
		// act
		Double precio = estacionamientoServiceImpl.generarReciboSalida(estacionamientoDTO, format.parse("18/12/2018 09:30:00"));
		
		// assert
		assertEquals(500, precio, 0);
	}
	
	@Test
	public void validarGeneracionReciboSalidaMotoEstacionadaDosDiasTest() throws ParseException {
		// arrange
		Vehiculo vehiculo = new VehiculoTestDataBuilder().conPlaca(PLACA_VALIDA_MOTO).build();
		EstacionamientoDTO estacionamientoDTO = new EstacionamientoTestDataBuilder().conVehiculo(vehiculo).conFechaIngreso(FECHA_DIA_HABIL).build();
		
		// act
		Double precio = estacionamientoServiceImpl.generarReciboSalida(estacionamientoDTO, format.parse("20/12/2018 04:00:00"));
		
		// assert
		assertEquals(8000, precio, 0);
	}
	
}
