package br.senai.sc.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sc.exceptions.NotFoundException;
import br.senai.sc.models.Conta;
import br.senai.sc.models.Pessoa;
import br.senai.sc.services.ContaService;
import br.senai.sc.services.HistoricoService;
import br.senai.sc.services.PessoaService;

@RestController
@RequestMapping("/contas")
public class ContaController {
	
	@Autowired
	private ContaService contaService;
	@Autowired
	private PessoaService pessoaService;
	@Autowired
	private HistoricoService historicoService;
	
	
	@GetMapping
	public List<Conta> buscarTodasContas() {
		return contaService.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Conta> buscarContaPorId(@PathVariable("id") Long id){
		return contaService.findById(id);
	}
	
	@GetMapping("/pessoa/{idPessoa}")
	public List<Conta> buscarContaPorPessoa(@PathVariable("idPessoa") Long idPessoa) throws NotFoundException{
		Optional<Pessoa> pessoa = pessoaService.findById(idPessoa);
		if(pessoa.isPresent()) {
			return contaService.findAllByPessoa(pessoa.get());
		} else {
			throw new NotFoundException();
		}
	}
	
	@GetMapping("/{idConta}/extrato")
	public Object buscaExtratoPorConta(@PathVariable("idConta") Long idConta){
		Optional<Conta> conta = contaService.findById(idConta);
		if(conta.isPresent()) {
			return historicoService.findAllByConta(conta.get());

		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
		}
	}
	
	@PostMapping
	public Object salvarConta(@RequestBody Conta conta) throws NotFoundException{
		try {
			if(pessoaService.findById(conta.getPessoa().getId()).isPresent()) {
				contaService.save(conta);
				return ResponseEntity.created(new URI("/contas/" + conta.getId())).body(conta);
			} else if (contaService.findById(conta.getId()).isPresent()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta já existe");
			} else {
				throw new NotFoundException();
			}
		} catch (NotFoundException nfe) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa Não encontrada");
		} catch (ConstraintViolationException cve){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Numero da conta ja existe");
		}
		catch (Exception e ) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		} 
	}
	
	@PutMapping
	public Object editarConta(@RequestBody Conta conta) throws NotFoundException{
		try {
			if(contaService.findById(conta.getId()).isPresent() || conta.getId() != null ) {
				contaService.save(conta);
				return new ResponseEntity<Conta>(conta, HttpStatus.CREATED);	
			} else {
				throw new NotFoundException();
			}			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta não encontrada");
		}
	}
	
	@DeleteMapping("/{id}")
	public Object deletarConta(@PathVariable("id") Long id) throws NotFoundException {
		try {
			if(contaService.findById(id).isPresent() || id != null) {
				Optional<Conta> conta = contaService.findById(id);
				contaService.deleteById(id);
				return ResponseEntity.status(HttpStatus.OK).body(conta);	
			} else if(!(contaService.findById(id).get().getHistorico()).isEmpty()){
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Não é possivel remover uma conta com historico de extrato");
			} else {
				throw new NotFoundException();			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encontrada");
		}
	}

}
