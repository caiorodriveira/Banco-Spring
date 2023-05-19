package br.senai.sc.models;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_historico")
public class Historico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_movimentacao")
	private Date data;
	
	@Column(name = "valor")
	private Double valor;
	
	@ManyToOne
	@JoinColumn(name="conta")
	@JsonIgnore
	private Conta conta;

	public Historico(Date data, Double valor, Conta conta) {
		super();
		this.data = data;
		this.valor = valor;
		this.conta = conta;
	}
	
	
}
