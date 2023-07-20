package com.desafio.trabalhador;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.desafio.cargo.Cargo;
import com.desafio.cargo.CargoRepository;
import com.desafio.setor.Setor;
import com.desafio.setor.SetorRepository;

@Service
public class TrabalhadorService {

	@Autowired
	private TrabalhadorRepository trabalhadorRepository;

	@Autowired
	private SetorRepository setorRepository;

	@Autowired
	private CargoRepository cargoRepository;

	public Trabalhador adicionarTrabalhador(Trabalhador trabalhador) {
		try {
			if (trabalhadorRepository.existsByCpf(trabalhador.getCpf())) {
				throw new IllegalArgumentException("Já existe um trabalhador cadastrado com esse CPF.");
			}

			if (!validarCPF(trabalhador.getCpf())) {
				throw new IllegalArgumentException("CPF inválido.");
			}

			Long setorId = trabalhador.getSetor().getId();
			Long cargoId = trabalhador.getCargo().getId();

			Setor setor = setorRepository.findById(setorId)
					.orElseThrow(() -> new IllegalArgumentException("Setor não encontrado com o ID: " + setorId));

			Cargo cargo = cargoRepository.findById(cargoId)
					.orElseThrow(() -> new IllegalArgumentException("Cargo não encontrado com o ID: " + cargoId));

			trabalhador.setSetor(setor);
			trabalhador.setCargo(cargo);

			return trabalhadorRepository.save(trabalhador);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

	}

	public Trabalhador editarTrabalhador(Long id, Trabalhador trabalhadorAtualizado) {
		try {
			Trabalhador trabalhadorExistente = trabalhadorRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Trabalhador não encontrado com o ID: " + id));

			trabalhadorExistente.setNome(trabalhadorAtualizado.getNome());
			trabalhadorExistente.setCpf(trabalhadorAtualizado.getCpf());
			trabalhadorExistente.setSetor(trabalhadorAtualizado.getSetor());
			trabalhadorExistente.setCargo(trabalhadorAtualizado.getCargo());

			Trabalhador trabalhadorEditado = trabalhadorRepository.save(trabalhadorExistente);
			return trabalhadorEditado;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

	}

	public boolean deletarTrabalhador(Long id) {
		try {
			Trabalhador trabalhadorExistente = trabalhadorRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Trabalhador não encontrado com o ID: " + id));
			trabalhadorRepository.delete(trabalhadorExistente);
			return true;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}
	}

	public List<Trabalhador> listarTrabalhadores() {
		return trabalhadorRepository.findAll();
	}

	public Trabalhador listarTrabalhadorById(Long id) {

		Trabalhador trabalhadorExistente = trabalhadorRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Trabalhador não encontrado com o ID: " + id));
		return trabalhadorExistente;

	}

	public boolean validarCPF(String cpf) {
		cpf = cpf.replaceAll("[^0-9]", "");

		if (cpf.length() != 11) {
			return false;
		}
		if (cpf.matches("(\\d)\\1{10}")) {
			return false;
		}
		try {
			int soma = 0;

			for (int i = 0; i < 9; i++) {
				soma += Character.getNumericValue(cpf.charAt(i)) * (10 - i);
			}

			int resto = soma % 11;
			int digitoVerificador1 = (resto < 2) ? 0 : 11 - resto;

			if (digitoVerificador1 != Character.getNumericValue(cpf.charAt(9))) {
				return false;
			}
			soma = 0;
			for (int i = 0; i < 10; i++) {
				soma += Character.getNumericValue(cpf.charAt(i)) * (11 - i);
			}
			resto = soma % 11;
			int digitoVerificador2 = (resto < 2) ? 0 : 11 - resto;
			return digitoVerificador2 == Character.getNumericValue(cpf.charAt(10));
		} catch (NumberFormatException e) {
			return false;
		}
	}

}
