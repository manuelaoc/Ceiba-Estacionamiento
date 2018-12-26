package com.ceiba.estacionamiento.service.impl.unitarias;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.ceiba.estacionamiento.controllers.EstacionamientoController;
import com.ceiba.estacionamiento.dto.EstacionamientoDTO;
import com.ceiba.estacionamiento.service.EstacionamientoService;
import com.ceiba.estacionamiento.testdatabuilder.EstacionamientoTestDataBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@WebMvcTest(EstacionamientoController.class)
public class EstacionamientoControllerTest {

	public static final String ESTACIONAMIENTO = "/api/estacionamiento";
	private ObjectMapper objectMapper = new ObjectMapper();
	
	@Autowired
	private MockMvc mocMvc;
	
	@MockBean
	EstacionamientoService estacionamientoService;
	
	@Test
	public void obtenerEstacionamientosTest() throws Exception {
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		List<EstacionamientoDTO> listaEstacionamientos = Arrays.asList(estacionamiento); 
		when(estacionamientoService.obtenerEstacionamientos()).thenReturn(listaEstacionamientos);
		
		mocMvc.perform(get(ESTACIONAMIENTO).contentType(MediaType.APPLICATION_JSON)).
			andExpect(status().isOk()).
			andExpect(jsonPath("$[0].vehiculo.placa", is(estacionamiento.getVehiculo().getPlaca())));
	}

	@Test
	public void obtenerEstacionamientoPorPlacaTest() throws Exception {
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		when(estacionamientoService.obtenerVehiculoEstacionado(estacionamiento.getVehiculo().getPlaca())).thenReturn(estacionamiento);
		
		mocMvc.perform(get(ESTACIONAMIENTO+"/{placa}",estacionamiento.getVehiculo().getPlaca()).contentType(MediaType.APPLICATION_JSON)).
			andExpect(status().isOk()).
			andExpect(content().string(containsString(estacionamiento.getVehiculo().getPlaca())));
	}
	
	@Test
	public void registrarIngresoEstacionamientoTest() throws Exception {
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		when(estacionamientoService.registrarIngresoEstacionamiento(estacionamiento)).thenReturn(estacionamiento);
		
		mocMvc.perform(post(ESTACIONAMIENTO).contentType(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(estacionamiento))).
			andExpect(status().isCreated());
	}
	
	@Test
	public void registrarSalidaEstacionamientoTest() throws Exception {
		EstacionamientoDTO estacionamiento = new EstacionamientoTestDataBuilder().build();
		when(estacionamientoService.registrarSalidaEstacionamiento(estacionamiento)).thenReturn(estacionamiento);
		
		mocMvc.perform(put(ESTACIONAMIENTO).contentType(MediaType.APPLICATION_JSON_UTF8).content(objectMapper.writeValueAsString(estacionamiento))).
			andExpect(status().isOk());
	}
}
