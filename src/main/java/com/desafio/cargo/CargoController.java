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
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CargoController {

	@Autowired
	private CargoService cargoService;

	@PostMapping("/cargo")
	public Cargo adicionarCargo(@Validated @RequestBody Cargo cargo) {
		return cargoService.adicionarCargo(cargo);
	}

	@PutMapping("/cargo/{id}")
	public Cargo editarCargo(@PathVariable Long id, @RequestBody Cargo cargoAtualizado) {
		return cargoService.editarCargo(id, cargoAtualizado);
	}

	@DeleteMapping("/cargo/{id}")
	public boolean deletarCargo(@PathVariable Long id) {
		return cargoService.deletarCargo(id);
	}

	@GetMapping("/cargo")
	public List<Cargo> listarCargos() {
		return cargoService.listarCargos();
	}
	
	@GetMapping("/cargo/{id}")
	public Cargo listarCargoById(@PathVariable Long id) {
		return cargoService.listarCargoById(id);
	}

}
