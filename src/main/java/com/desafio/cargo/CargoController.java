package com.desafio.cargo;

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
@Tag(name = "cargo")
@RequestMapping(value = "/cargo")
public class CargoController {

	@Autowired
	private CargoService cargoService;

	@PostMapping("")
	@Operation(summary = "Adiciona um cargo", method = "POST")
	public Cargo adicionarCargo(@Validated @RequestBody Cargo cargo) {
		return cargoService.adicionarCargo(cargo);
	}

	@PutMapping("/{id}")
	@Operation(summary = "Editar um cargo", method = "PUT")
	public Cargo editarCargo(@PathVariable Long id, @RequestBody Cargo cargoAtualizado) {
		return cargoService.editarCargo(id, cargoAtualizado);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar um cargo", method = "DELETE")
	public boolean deletarCargo(@PathVariable Long id) {
		return cargoService.deletarCargo(id);
	}

	@GetMapping("")
	@Operation(summary = "Listar todos os cargo", method = "GET")
	public List<Cargo> listarCargos() {
		return cargoService.listarCargos();
	}

	@GetMapping("/{id}")
	@Operation(summary = "Buscar um cargo por ID", method = "GET")
	public Cargo listarCargoById(@PathVariable Long id) {
		return cargoService.listarCargoById(id);
	}

}
