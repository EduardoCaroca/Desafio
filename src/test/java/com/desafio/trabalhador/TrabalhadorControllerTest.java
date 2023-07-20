package com.desafio.trabalhador;

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
@ContextConfiguration(classes = { TrabalhadorController.class, TrabalhadorService.class })
@WebMvcTest(TrabalhadorController.class)
public class TrabalhadorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private TrabalhadorService trabalhadorService;

	@Test
	public void testAdicionarTrabalhador() throws Exception {
		Trabalhador trabalhador = new Trabalhador();
		trabalhador.setNome("João");

		Trabalhador trabalhadorSalvo = new Trabalhador();
		trabalhadorSalvo.setId(1L);
		trabalhadorSalvo.setNome("João");

		when(trabalhadorService.adicionarTrabalhador(Mockito.any(Trabalhador.class))).thenReturn(trabalhadorSalvo);

		mockMvc.perform(post("/trabalhador").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(trabalhador))).andExpect(status().isOk());
	}

	@Test
	public void testEditarTrabalhador() throws Exception {
		Long trabalhadorId = 1L;
		Trabalhador trabalhadorAtualizado = new Trabalhador();
		trabalhadorAtualizado.setNome("Maria");

		Trabalhador trabalhadorEditado = new Trabalhador();
		trabalhadorEditado.setId(trabalhadorId);
		trabalhadorEditado.setNome("Maria");

		when(trabalhadorService.editarTrabalhador(Mockito.eq(trabalhadorId), Mockito.any(Trabalhador.class)))
				.thenReturn(trabalhadorEditado);

		mockMvc.perform(put("/trabalhador/{id}", trabalhadorId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(trabalhadorAtualizado))).andExpect(status().isOk());
	}

	@Test
	public void testDeletarTrabalhador() throws Exception {
		Long trabalhadorId = 1L;

		mockMvc.perform(delete("/trabalhador/{id}", trabalhadorId)).andExpect(status().isOk());
	}

	@Test
	public void testListarTrabalhadores() throws Exception {
		Trabalhador trabalhador1 = new Trabalhador();
		trabalhador1.setId(1L);
		trabalhador1.setNome("João");

		Trabalhador trabalhador2 = new Trabalhador();
		trabalhador2.setId(2L);
		trabalhador2.setNome("Maria");

		List<Trabalhador> trabalhadores = Arrays.asList(trabalhador1, trabalhador2);

		when(trabalhadorService.listarTrabalhadores()).thenReturn(trabalhadores);

		mockMvc.perform(get("/trabalhador")).andExpect(status().isOk());
	}

	@Test
	public void testListarTrabalhadorById() throws Exception {
		Long trabalhadorId = 1L;
		Trabalhador trabalhador = new Trabalhador();
		trabalhador.setId(trabalhadorId);
		trabalhador.setNome("João");

		when(trabalhadorService.listarTrabalhadorById(Mockito.eq(trabalhadorId))).thenReturn(trabalhador);

		mockMvc.perform(get("/trabalhador/{id}", trabalhadorId)).andExpect(status().isOk());
	}
}
