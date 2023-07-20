package com.desafio.trabalhador;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrabalhadorRepository extends JpaRepository<Trabalhador, Long> {
	
	  boolean existsByCpf(String cpf);

	public <S extends Trabalhador> S save(Trabalhador trabalhador);

}
