package com.desafio.cargo;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@ContextConfiguration(classes = { CargoController.class, CargoService.class })
@WebMvcTest(CargoController.class)
public class CargoControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private CargoService cargoService;

	@Test
	public void testAdicionarCargo() throws Exception {
		Cargo cargoToAdd = new Cargo();
		cargoToAdd.setNome("Cargo de Teste");

		Cargo cargoSalvo = new Cargo();
		cargoSalvo.setId(1L);
		cargoSalvo.setNome("Cargo de Teste");

		when(cargoService.adicionarCargo(Mockito.any(Cargo.class))).thenReturn(cargoSalvo);

		mockMvc.perform(post("/cargo").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cargoToAdd))).andExpect(status().isOk());
	}

	@Test
	public void testEditarCargo() throws Exception {
		Long cargoId = 1L;
		Cargo cargoAtualizado = new Cargo();
		cargoAtualizado.setNome("Cargo Editado");

		Cargo cargoEditado = new Cargo();
		cargoEditado.setId(cargoId);
		cargoEditado.setNome("Cargo Editado");

		when(cargoService.editarCargo(Mockito.eq(cargoId), Mockito.any(Cargo.class))).thenReturn(cargoEditado);

		mockMvc.perform(put("/cargo/{id}", cargoId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(cargoAtualizado))).andExpect(status().isOk());
	}

	@Test
	public void testDeletarCargo() throws Exception {
		Long cargoId = 1L;

		when(cargoService.deletarCargo(Mockito.eq(cargoId))).thenReturn(true);

		mockMvc.perform(delete("/cargo/{id}", cargoId)).andExpect(status().isOk());
	}

	@Test
	public void testListarCargos() throws Exception {
		Cargo cargo1 = new Cargo();
		cargo1.setId(1L);
		cargo1.setNome("Cargo 1");

		Cargo cargo2 = new Cargo();
		cargo2.setId(2L);
		cargo2.setNome("Cargo 2");

		List<Cargo> cargos = Arrays.asList(cargo1, cargo2);

		when(cargoService.listarCargos()).thenReturn(cargos);

		mockMvc.perform(get("/cargo")).andExpect(status().isOk());
	}

	@Test
	public void testListarCargoById() throws Exception {
		Long cargoId = 1L;

		Cargo cargo = new Cargo();
		cargo.setId(cargoId);
		cargo.setNome("Cargo 1");
		when(cargoService.listarCargoById(Mockito.eq(cargoId))).thenReturn(cargo);

		mockMvc.perform(get("/cargo/{id}", cargoId)).andExpect(status().isOk());
	}

}
