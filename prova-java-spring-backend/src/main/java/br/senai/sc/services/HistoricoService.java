package br.senai.sc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senai.sc.models.Conta;
import br.senai.sc.models.Historico;
import br.senai.sc.repositories.HistoricoRepository;

@Service
public class HistoricoService {
	
	@Autowired
	private HistoricoRepository repository;

	public List<Historico> findAll(){
		return repository.findAll();
	}
	
	public List<Historico> findAllByConta(Conta conta){
		return repository.findAllByConta(conta);
	}
	
	public void save(Historico historico) {
		repository.save(historico);
	}
	
	public Optional<Historico> findById(Long id) {
		return repository.findById(id);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
}
