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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TrabalhadorController {
	@Autowired
	private TrabalhadorService trabalhadorService;

	@PostMapping("/trabalhador")
	public Trabalhador adicionarCargo(@Validated @RequestBody Trabalhador trabalhador) {
		return trabalhadorService.adicionarTrabalhador(trabalhador);
	}

	@PutMapping("/trabalhador/{id}")
	public Trabalhador editarSetor(@PathVariable Long id, @RequestBody Trabalhador trabalhadorAtualizado) {
		return trabalhadorService.editarTrabalhador(id, trabalhadorAtualizado);
	}

	@DeleteMapping("/trabalhador/{id}")
	public boolean deletarTrabalhador(@PathVariable Long id) {
		return trabalhadorService.deletarTrabalhador(id);
	}

	@GetMapping("/trabalhador")
	public List<Trabalhador> listarTrabalhadores() {
		return trabalhadorService.listarTrabalhadores();
	}

	@GetMapping("/trabalhador/{id}")
	public Trabalhador listarTrabalhadorById(@PathVariable Long id) {
		return trabalhadorService.listarTrabalhadorById(id);
	}

}
