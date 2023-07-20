package com.desafio.cargo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.desafio.setor.Setor;

public interface CargoRepository extends JpaRepository<Cargo, Long> {
	
	boolean existsByNomeAndSetor(String nome, Setor setor);

}
