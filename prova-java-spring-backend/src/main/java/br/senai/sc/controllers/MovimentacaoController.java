package br.senai.sc.controllers;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sc.DTO.HistoricoDTO;
import br.senai.sc.models.Conta;
import br.senai.sc.models.Historico;
import br.senai.sc.movimentacoes.Movimentacao;
import br.senai.sc.services.ContaService;
import br.senai.sc.services.HistoricoService;

@RestController
@RequestMapping("movimentacao")
public class MovimentacaoController {

	@Autowired
	private HistoricoService historicoService;
	@Autowired
	private ContaService contaService;

	@PostMapping("/saque")
	public Object saque(@RequestBody Movimentacao saque) {
		try {
			Optional<Conta> conta = contaService.findById(saque.getIdConta());
			if (conta.isEmpty()) {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Conta não encotrada");
			} else {
				if (saque.getValor() > conta.get().getSaldo() || conta.get().getSaldo() == null) {
					return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body("Saldo insuficiente");
				} else {
					try {
						Double result = (conta.get().getSaldo()==null ? 0f : conta.get().getSaldo()) - saque.getValor();
						conta.get().setSaldo(result);
						contaService.save(conta.get());
						Historico historico = new Historico(saque.getDataMovimentacao(), (-saque.getValor()),
								conta.get());
						historicoService.save(historico);
						return ResponseEntity.created(new URI("/saque/conta/" + conta.get().getId())).body(new HistoricoDTO(historico));
					} catch (URISyntaxException e) {
						return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
					}
				}
			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

	@PostMapping("/deposito")
	public Object deposito(@RequestBody Movimentacao deposito) {
		try {
			Optional<Conta> conta = contaService.findById(deposito.getIdConta());
			if (conta.isEmpty()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Conta não encotrada");
			} else {
				try {
					Double result = (conta.get().getSaldo()==null ? 0f : conta.get().getSaldo()) + deposito.getValor();
					conta.get().setSaldo(result);
					System.out.println(result);
					contaService.save(conta.get());
					Historico historico = new Historico(deposito.getDataMovimentacao(), deposito.getValor(), conta.get());
					historicoService.save(historico);
					return ResponseEntity.created(new URI("/deposito/conta/" + conta.get().getId())).body(new HistoricoDTO(historico));
				} catch (URISyntaxException e) {
					return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Erro realizar deposito");
				}

			}

		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
		}

	}

}
