package com.desafio.setor;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SetorService {

	@Autowired
	private SetorRepository setorRepository;

	public Setor adicionarSetor(Setor setor) {
		try {
			if (setorRepository.existsByNome(setor.getNome())) {
				throw new IllegalArgumentException("Já existe um setor com o mesmo nome.");
			}
			return setorRepository.save(setor);
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}

	}

	public Setor editarSetor(Long id, Setor setorAtualizado) {
		try {
			Setor setorExistente = setorRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Setor não encontrado com o ID: " + id));

			setorExistente.setNome(setorAtualizado.getNome());

			Setor setorEditado = setorRepository.save(setorExistente);
			return setorEditado;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
	}

	public boolean deletarSetor(Long id) {
		try {
			Setor setorExistente = setorRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Setor não encontrado com o ID: " + id));
			setorRepository.delete(setorExistente);
			return true;
		} catch (Exception e) {
			System.err.println(e);
			return false;
		}

	}

	public List<Setor> listarSetores() {
		return setorRepository.findAll();
	}

	public Setor listarSetorById(Long id) {
		try {
			Setor setorExistente = setorRepository.findById(id)
					.orElseThrow(() -> new IllegalArgumentException("Setor não encontrado com o ID: " + id));
			return setorExistente;
		} catch (Exception e) {
			System.err.println(e);
			return null;
		}
		
	}

}
