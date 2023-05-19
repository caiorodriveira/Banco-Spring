package br.senai.sc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.senai.sc.models.Conta;
import br.senai.sc.models.Pessoa;
import br.senai.sc.repositories.ContaRepository;

@Service
public class ContaService {
	
	@Autowired
	private ContaRepository repository;
	
	public List<Conta> findAll(){
		return repository.findAll();
	}
	
	public void save(Conta conta) {
		repository.save(conta);
	}
	
	public Optional<Conta> findById(Long id) {
		return repository.findById(id);
	}
	
	public void deleteById(Long id) {
		repository.deleteById(id);
	}
	
	public List<Conta> findAllByPessoa(Pessoa pessoa){
		return repository.findAllByPessoa(pessoa);
	}
}
