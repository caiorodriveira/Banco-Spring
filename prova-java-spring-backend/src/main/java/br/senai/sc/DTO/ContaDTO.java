package br.senai.sc.DTO;

import br.senai.sc.models.Conta;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContaDTO {
	private Long id;
	private Long pessoa;
	private Long numeroConta;
	private Double saldo;
	
	public ContaDTO(Conta conta) {
		this.id = conta.getId();
		this.pessoa = conta.getPessoa().getId();
		this.id = conta.getId();
		this.id = conta.getId();
	}
}
