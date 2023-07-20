package com.desafio.trabalhador;

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

import com.desafio.cargo.CargoRepository;
import com.desafio.setor.SetorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class TrabalhadorServiceTest {

	@MockBean
	private TrabalhadorRepository trabalhadorRepository;

	@MockBean
	private SetorRepository setorRepository;

	@MockBean
	private CargoRepository cargoRepository;

	@Autowired
	private TrabalhadorService trabalhadorService;

	@Test
	public void testAdicionarTrabalhador_CpfExistente() {
		String cpf = "12345678901";

		Trabalhador trabalhador = new Trabalhador();
		trabalhador.setCpf(cpf);

		when(trabalhadorRepository.existsByCpf(cpf)).thenReturn(true);

		Assertions.assertEquals(trabalhadorService.adicionarTrabalhador(trabalhador), null);
		verify(trabalhadorRepository, never()).save(Mockito.any(Trabalhador.class));
	}

	@Test
	public void testAdicionarTrabalhador_CpfInvalido() {
		String cpf = "12345678901";

		Trabalhador trabalhador = new Trabalhador();
		trabalhador.setCpf(cpf);

		when(trabalhadorRepository.existsByCpf(cpf)).thenReturn(false);
		Assertions.assertEquals(trabalhadorService.adicionarTrabalhador(trabalhador), null);
		verify(trabalhadorRepository, never()).save(Mockito.any(Trabalhador.class));
	}

	@Test
	public void testEditarTrabalhador_TrabalhadorExistente() {
		Long trabalhadorId = 1L;
		Trabalhador trabalhadorExistente = new Trabalhador();
		trabalhadorExistente.setId(trabalhadorId);
		trabalhadorExistente.setNome("Trabalhador Antigo");

		Trabalhador trabalhadorAtualizado = new Trabalhador();
		trabalhadorAtualizado.setNome("Trabalhador Editado");

		when(trabalhadorRepository.findById(trabalhadorId)).thenReturn(Optional.of(trabalhadorExistente));
		when(trabalhadorRepository.save(trabalhadorExistente)).thenReturn(trabalhadorExistente);

		Trabalhador resultado = trabalhadorService.editarTrabalhador(trabalhadorId, trabalhadorAtualizado);

		Assertions.assertNotNull(resultado);
		Assertions.assertEquals(trabalhadorAtualizado.getNome(), resultado.getNome());
		Assertions.assertEquals(trabalhadorId, resultado.getId());

		verify(trabalhadorRepository, times(1)).findById(trabalhadorId);
		verify(trabalhadorRepository, times(1)).save(trabalhadorExistente);
	}

	@Test
	public void testEditarTrabalhador_TrabalhadorNaoEncontrado() {
		Long trabalhadorId = 1L;
		Trabalhador trabalhadorAtualizado = new Trabalhador();
		trabalhadorAtualizado.setNome("Trabalhador Editado");

		when(trabalhadorRepository.findById(trabalhadorId)).thenReturn(Optional.empty());

		Trabalhador resultado = trabalhadorService.editarTrabalhador(trabalhadorId, trabalhadorAtualizado);

		Assertions.assertNull(resultado);

		verify(trabalhadorRepository, times(1)).findById(trabalhadorId);
		verify(trabalhadorRepository, never()).save(Mockito.any());
	}

	@Test
	public void testDeletarTrabalhador_TrabalhadorExistente() {
		Long trabalhadorId = 1L;
		Trabalhador trabalhadorExistente = new Trabalhador();
		trabalhadorExistente.setId(trabalhadorId);
		trabalhadorExistente.setNome("Trabalhador Existente");

		when(trabalhadorRepository.findById(trabalhadorId)).thenReturn(Optional.of(trabalhadorExistente));

		boolean resultado = trabalhadorService.deletarTrabalhador(trabalhadorId);

		Assertions.assertTrue(resultado);

		verify(trabalhadorRepository, times(1)).findById(trabalhadorId);
		verify(trabalhadorRepository, times(1)).delete(trabalhadorExistente);
	}

	@Test
	public void testDeletarTrabalhador_TrabalhadorNaoEncontrado() {
		Long trabalhadorId = 1L;

		when(trabalhadorRepository.findById(trabalhadorId)).thenReturn(Optional.empty());

		boolean resultado = trabalhadorService.deletarTrabalhador(trabalhadorId);

		Assertions.assertFalse(resultado);

		verify(trabalhadorRepository, times(1)).findById(trabalhadorId);
		verify(trabalhadorRepository, never()).delete(Mockito.any());
	}

	@Test
	public void testListarTrabalhadores() {
		List<Trabalhador> listaTrabalhadores = new ArrayList<>();
		listaTrabalhadores.add(new Trabalhador());
		listaTrabalhadores.add(new Trabalhador());

		when(trabalhadorRepository.findAll()).thenReturn(listaTrabalhadores);

		List<Trabalhador> resultado = trabalhadorService.listarTrabalhadores();

		Assertions.assertEquals(listaTrabalhadores.size(), resultado.size());

		verify(trabalhadorRepository, times(1)).findAll();
	}

	@Test
	public void testListarTrabalhadorById_TrabalhadorExistente() {
		Long trabalhadorId = 1L;
		Trabalhador trabalhadorExistente = new Trabalhador();
		trabalhadorExistente.setId(trabalhadorId);
		trabalhadorExistente.setNome("Trabalhador Existente");

		when(trabalhadorRepository.findById(trabalhadorId)).thenReturn(Optional.of(trabalhadorExistente));

		Trabalhador resultado = trabalhadorService.listarTrabalhadorById(trabalhadorId);

		Assertions.assertNotNull(resultado);
		Assertions.assertEquals(trabalhadorId, resultado.getId());

		verify(trabalhadorRepository, times(1)).findById(trabalhadorId);
	}

	@Test
	public void testListarTrabalhadorById_TrabalhadorNaoEncontrado() {
		Long trabalhadorId = 1L;

		when(trabalhadorRepository.findById(trabalhadorId)).thenReturn(Optional.empty());

		Trabalhador resultado = trabalhadorService.listarTrabalhadorById(trabalhadorId);

		Assertions.assertNull(resultado);

		verify(trabalhadorRepository, times(1)).findById(trabalhadorId);
	}

	@Test
	public void testValidarCPFCPFValido() {
		String cpfValido = "12345678909";
		Assertions.assertTrue(trabalhadorService.validarCPF(cpfValido));
	}

	@Test
	public void testValidarCPFCPFInvalido() {
		String cpfInvalido = "12345678900";
		Assertions.assertFalse(trabalhadorService.validarCPF(cpfInvalido));
	}

	@Test
	public void testValidarCPFCPFComCaracteresInvalidos() {
		String cpfComCaracteresInvalidos = "123.456.789-09";
		Assertions.assertTrue(trabalhadorService.validarCPF(cpfComCaracteresInvalidos));
	}

	@Test
	public void testValidarCPFCPFComMenosDe11Digitos() {
		String cpfComMenosDe11Digitos = "1234567890";
		Assertions.assertFalse(trabalhadorService.validarCPF(cpfComMenosDe11Digitos));
	}

}
