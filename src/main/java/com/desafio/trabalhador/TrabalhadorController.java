package com.desafio.trabalhador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "trabalhador")
@RequestMapping(value = "/trabalhador")
public class TrabalhadorController {
	@Autowired
	private TrabalhadorService trabalhadorService;

	@PostMapping("")
	@Operation(summary = "Adiciona um trabalhador com um cargo e setor", method = "POST")
	public Trabalhador adicionarCargo(@Validated @RequestBody Trabalhador trabalhador) {
		return trabalhadorService.adicionarTrabalhador(trabalhador);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Editar um trabalhador", method = "PUT")
	public Trabalhador editarSetor(@PathVariable Long id, @RequestBody Trabalhador trabalhadorAtualizado) {
		return trabalhadorService.editarTrabalhador(id, trabalhadorAtualizado);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar um trabalhador", method = "DELETE")
	public boolean deletarTrabalhador(@PathVariable Long id) {
		return trabalhadorService.deletarTrabalhador(id);
	}

	@GetMapping("")
	@Operation(summary = "Listar todos os trabalhadores", method = "GET")
	public List<Trabalhador> listarTrabalhadores() {
		return trabalhadorService.listarTrabalhadores();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar um trabalhador por ID", method = "GET")
	public Trabalhador listarTrabalhadorById(@PathVariable Long id) {
		return trabalhadorService.listarTrabalhadorById(id);
	}

}
