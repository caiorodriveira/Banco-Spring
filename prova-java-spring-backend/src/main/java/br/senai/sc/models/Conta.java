package br.senai.sc.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tb_conta")
@PrimaryKeyJoinColumn(name = "id")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Conta {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "pessoa")
	@NotNull(message = "Campo obrigatório")
	private Pessoa pessoa;
	
	
	@Column(nullable = false, name = "numero_conta", unique = true)
	@NotNull(message = "Campo obrigatório")
	private Long numeroConta;
	
	private Double saldo;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "conta")
	@JsonManagedReference
	private List<Historico> historico = new ArrayList<>();

	public Conta(Long id, Pessoa pessoa, Long numeroConta, Double saldo) {
		super();
		this.id = id;
		this.pessoa = pessoa;
		this.numeroConta = numeroConta;
		this.saldo = saldo;
	};

	
	
	
}

