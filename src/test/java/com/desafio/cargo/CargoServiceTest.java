package com.desafio.cargo;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import com.desafio.setor.Setor;
import com.desafio.setor.SetorRepository;

@SpringBootTest
@AutoConfigureMockMvc
public class CargoServiceTest {

	@Mock
	private CargoRepository cargoRepository;

	@Mock
	private SetorRepository setorRepository;

	@InjectMocks
	private CargoService cargoService;

	@Test
	public void testAdicionarCargo_Sucesso() {
		Long setorId = 1L;
		String cargoNome = "Cargo de Teste";
		Cargo cargo = new Cargo();
		cargo.setNome(cargoNome);

		Setor setorExistente = new Setor();
		setorExistente.setId(setorId);
		setorExistente.setNome("Setor Teste");
		cargo.setSetor(setorExistente);

		when(setorRepository.findById(Mockito.any())).thenReturn(Optional.of(setorExistente));
		when(cargoRepository.existsByNomeAndSetor(cargoNome, setorExistente)).thenReturn(false);
		when(cargoRepository.save(Mockito.any(Cargo.class))).thenReturn(cargo);

		Cargo resultado = cargoService.adicionarCargo(cargo);
		Assertions.assertEquals(cargo, resultado);
	}

	@Test
	public void testAdicionarCargo_SetorNaoEncontrado() {
		String cargoNome = "Cargo de Teste";
		Cargo cargo = new Cargo();
		cargo.setNome(cargoNome);

		when(setorRepository.findById(Mockito.anyLong())).thenReturn(Optional.empty());
		Assertions.assertEquals(cargoService.adicionarCargo(cargo), null);
		verify(cargoRepository, never()).save(Mockito.any(Cargo.class));
	}

	@Test
	public void testAdicionarCargo_CargoJaVinculadoAoSetor() {
		Long setorId = 1L;
		String cargoNome = "Cargo de Teste";
		Cargo cargo = new Cargo();
		cargo.setNome(cargoNome);

		Setor setorExistente = new Setor();
		setorExistente.setId(setorId);
		setorExistente.setNome("Setor Teste");

		when(setorRepository.findById(setorId)).thenReturn(Optional.of(setorExistente));
		when(cargoRepository.existsByNomeAndSetor(cargoNome, setorExistente)).thenReturn(true);

		Assertions.assertEquals(cargoService.adicionarCargo(cargo), null);
		verify(cargoRepository, never()).save(Mockito.any(Cargo.class));
	}

	@Test
	public void testEditarCargo_CargoExistente() {
		Long cargoId = 1L;
		String novoNome = "Cargo Editado";
		Cargo cargoExistente = new Cargo();
		cargoExistente.setId(cargoId);
		cargoExistente.setNome("Cargo Antigo");

		Cargo cargoAtualizado = new Cargo();
		cargoAtualizado.setNome(novoNome);

		when(cargoRepository.findById(cargoId)).thenReturn(Optional.of(cargoExistente));
		when(cargoRepository.save(cargoExistente)).thenReturn(cargoExistente);

		Cargo resultado = cargoService.editarCargo(cargoId, cargoAtualizado);
		Assertions.assertNotNull(resultado);
		Assertions.assertEquals(novoNome, resultado.getNome());
		Assertions.assertEquals(cargoId, resultado.getId());
		verify(cargoRepository, times(1)).save(cargoExistente);
	}

	@Test
	public void testEditarCargo_CargoNaoEncontrado() {
		Long cargoId = 1L;
		String novoNome = "Cargo Editado";
		Cargo cargoAtualizado = new Cargo();
		cargoAtualizado.setNome(novoNome);

		when(cargoRepository.findById(cargoId)).thenReturn(Optional.empty());

		Assertions.assertEquals(cargoService.editarCargo(cargoId, cargoAtualizado), null);

		verify(cargoRepository, never()).save(Mockito.any(Cargo.class));
	}

	@Test
	public void testDeletarCargo_CargoExistente() {
		Long cargoId = 1L;

		Cargo cargoExistente = new Cargo();
		cargoExistente.setId(cargoId);
		cargoExistente.setNome("Cargo Teste");

		when(cargoRepository.findById(cargoId)).thenReturn(Optional.of(cargoExistente));

		boolean resultado = cargoService.deletarCargo(cargoId);

		Assertions.assertTrue(resultado);
		verify(cargoRepository, times(1)).delete(cargoExistente);
	}

	@Test
	public void testDeletarCargo_CargoNaoEncontrado() {
		Long cargoId = 1L;

		when(cargoRepository.findById(cargoId)).thenReturn(Optional.empty());

		boolean resultado = cargoService.deletarCargo(cargoId);

		Assertions.assertFalse(resultado);
		verify(cargoRepository, never()).delete(Mockito.any(Cargo.class));
	}

	@Test
	public void testListarCargos() {
		Cargo cargo1 = new Cargo();
		cargo1.setId(1L);
		cargo1.setNome("Cargo 1");

		Cargo cargo2 = new Cargo();
		cargo2.setId(2L);
		cargo2.setNome("Cargo 2");

		List<Cargo> cargos = List.of(cargo1, cargo2);

		when(cargoRepository.findAll()).thenReturn(cargos);

		List<Cargo> resultado = cargoService.listarCargos();
		Assertions.assertEquals(cargos, resultado);
	}

	@Test
	public void testListarCargoById_CargoExistente() {
		Long cargoId = 1L;
		Cargo cargoExistente = new Cargo();
		cargoExistente.setId(cargoId);
		cargoExistente.setNome("Cargo Teste");

		when(cargoRepository.findById(cargoId)).thenReturn(Optional.of(cargoExistente));

		Cargo resultado = cargoService.listarCargoById(cargoId);
		Assertions.assertEquals(cargoExistente, resultado);
	}

	@Test
	public void testListarCargoById_CargoNaoEncontrado() {
		Long cargoId = 1L;

		when(cargoRepository.findById(cargoId)).thenReturn(Optional.empty());

		Cargo resultado = cargoService.listarCargoById(cargoId);
		Assertions.assertNull(resultado);
	}

}
