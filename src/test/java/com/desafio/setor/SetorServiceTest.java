package com.desafio.setor;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
@AutoConfigureMockMvc
public class SetorServiceTest {

	@MockBean
	private SetorRepository setorRepository;

	@Autowired
	private SetorService setorService;

	@Test
	public void testAdicionarSetor() {
		Setor setor = new Setor();
		setor.setNome("Setor de Teste");

		when(setorRepository.existsByNome(setor.getNome())).thenReturn(false);

		Setor setorSalvo = new Setor();
		setorSalvo.setId(1L);
		setorSalvo.setNome("Setor de Teste");
		when(setorRepository.save(Mockito.any(Setor.class))).thenReturn(setorSalvo);

		Setor resultado = setorService.adicionarSetor(setor);

		Assertions.assertEquals(setorSalvo, resultado);
	}

	@Test
	public void testEditarSetor_SetorExistente() {
		Long setorId = 1L;
		String novoNome = "Novo Setor";
		Setor setorExistente = new Setor();
		setorExistente.setId(setorId);
		setorExistente.setNome("Setor Antigo");

		Setor setorAtualizado = new Setor();
		setorAtualizado.setNome(novoNome);

		when(setorRepository.findById(setorId)).thenReturn(Optional.of(setorExistente));
		when(setorRepository.save(setorExistente)).thenReturn(setorExistente);

		Setor resultado = setorService.editarSetor(setorId, setorAtualizado);
		Assertions.assertNotNull(resultado);
		Assertions.assertEquals(novoNome, resultado.getNome());
		Assertions.assertEquals(setorId, resultado.getId());

		verify(setorRepository, times(1)).findById(setorId);
		verify(setorRepository, times(1)).save(setorExistente);
	}

	@Test
	public void testEditarSetor_SetorNaoEncontrado() {
		Long setorId = 1L;
		String novoNome = "Novo Setor";
		Setor setorAtualizado = new Setor();
		setorAtualizado.setNome(novoNome);

		when(setorRepository.findById(setorId)).thenReturn(Optional.empty());

		Setor resultado = setorService.editarSetor(setorId, setorAtualizado);
		Assertions.assertEquals(resultado, null);

		verify(setorRepository, times(1)).findById(setorId);
		verify(setorRepository, never()).save(Mockito.any());
	}

	@Test
	public void testDeletarSetor_SetorEncontrado() {
		Long setorId = 1L;
		Setor setorExistente = new Setor();
		setorExistente.setId(setorId);

		when(setorRepository.findById(setorId)).thenReturn(Optional.of(setorExistente));

		boolean deletado = setorService.deletarSetor(setorId);

		Assertions.assertTrue(deletado);

		verify(setorRepository, times(1)).delete(setorExistente);
	}

	@Test
	public void testDeletarSetor_SetorNaoEncontrado() {
		Long setorId = 1L;

		when(setorRepository.findById(setorId)).thenReturn(Optional.empty());

		boolean deletado = setorService.deletarSetor(setorId);

		Assertions.assertFalse(deletado);

		verify(setorRepository, never()).delete(Mockito.any());
	}

	@Test
	public void testListarSetores() {
		// Criar uma lista de Setor de teste
		List<Setor> setores = new ArrayList<>();
		Setor setor1 = new Setor();
		setor1.setId(1L);
		setor1.setNome("Setor 1");
		Setor setor2 = new Setor();
		setor2.setId(2L);
		setor2.setNome("Setor 2");
		setores.add(setor1);
		setores.add(setor2);

		when(setorRepository.findAll()).thenReturn(setores);

		List<Setor> result = setorService.listarSetores();

		Assertions.assertEquals(setores, result);
	}

	@Test
	public void testListarSetorById_SetorExistente() {
		Long setorId = 1L;
		Setor setorExistente = new Setor();
		setorExistente.setId(setorId);
		setorExistente.setNome("Setor Existente");

		when(setorRepository.findById(setorId)).thenReturn(Optional.of(setorExistente));

		Setor result = setorService.listarSetorById(setorId);

		Assertions.assertEquals(setorExistente, result);
	}

	@Test
	public void testListarSetorById_SetorNaoEncontrado() {
		Long setorId = 1L;

		when(setorRepository.findById(setorId)).thenReturn(Optional.empty());

		Assertions.assertEquals(setorService.listarSetorById(setorId), null);
	}

}
