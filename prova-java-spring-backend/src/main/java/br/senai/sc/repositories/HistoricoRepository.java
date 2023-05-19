package br.senai.sc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.senai.sc.models.Conta;
import br.senai.sc.models.Historico;

public interface HistoricoRepository extends JpaRepository<Historico, Long>{
	
	public List<Historico> findAllByConta(Conta conta);
}
