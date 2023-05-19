package br.senai.sc.movimentacoes;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Movimentacao {

	private Tipo tipo;
	private Double valor;
	private Long idConta;
	private Date dataMovimentacao;
	
}

enum Tipo{
	SAQUE,
	DEPOSITO
}
