package com.desafio.cargo;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.setor.Setor;
import com.desafio.setor.SetorRepository;

@Service
public class CargoService {

	@Autowired
	private CargoRepository cargoRepository;

	@Autowired
	private SetorRepository setorRepository;

	public Cargo adicionarCargo(Cargo cargo) {
		try {
			if (cargo.getSetor() != null && cargo.getSetor().getId() != null) {
				Setor setorBanco = setorRepository.findById(cargo.getSetor().getId()).orElse(null);
				if (setorBanco != null) {
					if (cargoRepository.existsByNomeAndSetor(cargo.getNome(), setorBanco)) {
						throw new IllegalArgumentException("Este cargo já está vinculado a outro setor.");
					}
					cargo.setSetor(setorBanco);
					return cargoRepository.save(cargo);
				} else {
					throw new IllegalArgumentException("Setor não encontrado com o ID fornecido.");
				}
			} else {
				throw new IllegalArgumentException("Setor inválido.");
			}
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
		
	}

	public Cargo editarCargo(Long id, Cargo cargoAtualizado) {
		try {
			Cargo cargoExistente = cargoRepository.findById(id)
					.orElseThrow(() -> new NoSuchElementException("Cargo não encontrado"));

			cargoExistente.setNome(cargoAtualizado.getNome());

			return cargoRepository.save(cargoExistente);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	public boolean deletarCargo(Long id) {
		try {
			Cargo cargoExistente = cargoRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Cargo não encontrado com o ID: " + id));
			cargoRepository.delete(cargoExistente);
			return true;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
	
	}

	public List<Cargo> listarCargos() {
		return cargoRepository.findAll();
	}

	public Cargo listarCargoById(Long id) {
		try {
			Cargo cargoExistente = cargoRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Cargo não encontrado com o ID: " + id));
			return cargoExistente;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

}
