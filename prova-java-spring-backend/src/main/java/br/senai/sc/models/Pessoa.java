package br.senai.sc.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "tb_pessoas")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Pessoa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "Nome Obrigatório!")
	@Column(nullable = false, unique = true)
	private String nome;
	
	@NotEmpty(message = "CPF Obrigatório!")
	@Column(nullable = false, unique = true) 
	private String cpf;
	
	@NotEmpty(message = "Endereço Obrigatório!")
	@Column(nullable = false)
	private String endereco;

	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "pessoa")
	private List<Conta> contas = new ArrayList<>();
	


}
