//package com.desafio.setor;
//
//import static org.mockito.Mockito.when;
//
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//@SpringBootTest
//public class SetorServiceTest {
//
//	@Autowired
//	private SetorRepository setorRepository;
//	
//	@Autowired
//	private SetorService setorService;
//
//	@Test
//	private void testAdicionarSetor() {
//		Setor setor = new Setor();
//		setor.setNome("Setor de Teste");
//
//		when(setorRepository.existsByNome(setor.getNome())).thenReturn(false);
//
//		Setor setorSalvo = new Setor();
//		setorSalvo.setId(1L);
//		setorSalvo.setNome("Setor de Teste");
//		when(setorRepository.save(Mockito.any(Setor.class))).thenReturn(setorSalvo);
//
//		Setor resultado = setorService.adicionarSetor(setor);
//
//		Assertions.assertEquals(setorSalvo, resultado);
//	}
//}
