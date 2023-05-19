package br.senai.sc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sc.models.Conta;
import br.senai.sc.models.Pessoa;

public interface ContaRepository extends JpaRepository<Conta, Long>{

	List<Conta> findAllByPessoa(Pessoa pessoa);
}
