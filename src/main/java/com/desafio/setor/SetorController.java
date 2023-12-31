package com.desafio.setor;

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
@Tag(name = "setor")
@RequestMapping(value = "/setor")
public class SetorController {
	@Autowired
	private SetorService setorService;

	@PostMapping("")
	@Operation(summary = "Adiciona um setor com um cargo", method = "POST")
	public Setor adicionarSetor(@Validated @RequestBody Setor setor) {
		return setorService.adicionarSetor(setor);
	}
	
	@PutMapping("/{id}")
	@Operation(summary = "Editar um setor", method = "PUT")
	public Setor editarSetor(@PathVariable Long id, @RequestBody Setor setorAtualizado) {
		return setorService.editarSetor(id, setorAtualizado);
	}
	
	@DeleteMapping("/{id}")
	@Operation(summary = "Deletar um setor", method = "DELETE")
	public boolean deletarSetor(@PathVariable Long id) {
		return setorService.deletarSetor(id);
	}

	@GetMapping("")
	@Operation(summary = "Listar todos os setores", method = "GET")
	public List<Setor> listarSetor() {
		return setorService.listarSetores();
	}
	
	@GetMapping("/{id}")
	@Operation(summary = "Buscar um setor por ID", method = "GET")
	public Setor listarSetorById(@PathVariable Long id) {
		return setorService.listarSetorById(id);
	}
	
}
