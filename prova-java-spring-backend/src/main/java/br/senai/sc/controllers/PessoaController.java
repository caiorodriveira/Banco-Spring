package br.senai.sc.controllers;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.senai.sc.exceptions.NotFoundException;
import br.senai.sc.models.Pessoa;
import br.senai.sc.services.PessoaService;

@RestController
@RequestMapping("/pessoas")
@CrossOrigin
public class PessoaController {

	@Autowired
	private PessoaService service;	
	
	@GetMapping
	public List<Pessoa> buscarTodasPessoas() {
		return service.findAll();
	}
	
	@GetMapping("/{id}")
	public Optional<Pessoa> buscarPessoaPorId(@PathVariable("id") Long id){
		return service.findById(id);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> salvarPessoa(@RequestBody Pessoa pessoa){
		try {
			service.save(pessoa);
			return ResponseEntity.created(new URI("/pessoas/" + pessoa.getId())).body(pessoa);		
		} catch (Exception e) {
			System.out.println(pessoa);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
		}
	}
	@PutMapping
	public Object editarPessoa(@RequestBody Pessoa pessoa) throws NotFoundException{
		try {
			if(service.findById(pessoa.getId()).isPresent() || pessoa.getId() != null ) {
				service.save(pessoa);
				return new ResponseEntity<Pessoa>(pessoa, HttpStatus.CREATED);	
			} else {
				throw new NotFoundException();
			}			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
		}
	}
	
	@DeleteMapping("/{id}")
	public Object deletarPessoa(@PathVariable("id") Long id) throws NotFoundException {
		try {
			if(service.findById(id).isPresent() || id != null) {
				Optional<Pessoa> pessoa = service.findById(id);
				service.deleteById(id);
				return ResponseEntity.status(HttpStatus.OK).body(pessoa);	
			} else {
				throw new NotFoundException();			}
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pessoa não encontrada");
		}
	}
	
	
}
