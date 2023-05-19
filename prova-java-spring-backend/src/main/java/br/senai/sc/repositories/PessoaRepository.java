package br.senai.sc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sc.models.Pessoa;

public interface PessoaRepository  extends JpaRepository<Pessoa, Long>{

}
