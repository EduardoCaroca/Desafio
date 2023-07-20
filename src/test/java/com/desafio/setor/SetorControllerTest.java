package com.desafio.setor;

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
@ContextConfiguration(classes = { SetorController.class, SetorService.class })
@WebMvcTest(SetorController.class)
public class SetorControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SetorService setorService;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	public void testAdicionarSetor() throws Exception {
		Setor setor = new Setor();
		setor.setNome("Setor de Teste");

		Setor setorSalvo = new Setor();
		setorSalvo.setId(1L);
		setorSalvo.setNome("Setor de Teste");
		when(setorService.adicionarSetor(Mockito.any(Setor.class))).thenReturn(setorSalvo);

		mockMvc.perform(
				post("/setor").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(setor)))
				.andExpect(status().isOk());
	}

	@Test
	public void testEditarSetor() throws Exception {
		Long setorId = 1L;
		Setor setorAtualizado = new Setor();
		setorAtualizado.setNome("Setor Atualizado");

		Setor setorEditado = new Setor();
		setorEditado.setId(setorId);
		setorEditado.setNome("Setor Atualizado");
		when(setorService.editarSetor(Mockito.eq(setorId), Mockito.any(Setor.class))).thenReturn(setorEditado);

		mockMvc.perform(put("/setor/{id}", setorId).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(setorAtualizado))).andExpect(status().isOk());
	}

	@Test
	public void testDeletarSetor() throws Exception {
		Long setorId = 1L;

		when(setorService.deletarSetor(Mockito.eq(setorId))).thenReturn(true);

		mockMvc.perform(delete("/setor/{id}", setorId)).andExpect(status().isOk());
	}

	@Test
	public void testListarSetor() throws Exception {
		Setor setor1 = new Setor();
		setor1.setId(1L);
		setor1.setNome("Setor 1");

		Setor setor2 = new Setor();
		setor2.setId(2L);
		setor2.setNome("Setor 2");

		List<Setor> setores = Arrays.asList(setor1, setor2);

		when(setorService.listarSetores()).thenReturn(setores);

		mockMvc.perform(get("/setor")).andExpect(status().isOk());
	}

	@Test
	public void testListarSetorById() throws Exception {
		Long setorId = 1L;
		Setor setor = new Setor();
		setor.setId(setorId);
		setor.setNome("Setor 1");
		when(setorService.listarSetorById(Mockito.eq(setorId))).thenReturn(setor);

		mockMvc.perform(get("/setor/{id}", setorId)).andExpect(status().isOk());
	}

}
