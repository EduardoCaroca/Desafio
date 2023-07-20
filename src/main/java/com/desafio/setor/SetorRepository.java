package com.desafio.setor;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SetorRepository extends JpaRepository<Setor, Long> {

	boolean existsByNome(String nome);
	
	public <S extends Setor> S save(Setor setor);

}
