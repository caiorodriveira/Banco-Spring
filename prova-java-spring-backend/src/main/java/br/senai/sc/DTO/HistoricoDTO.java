package br.senai.sc.DTO;

import java.sql.Date;

import br.senai.sc.models.Historico;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HistoricoDTO {
	private Long id;
	private Date data;
	private Double valor;
	private Long conta;
	
	
	public HistoricoDTO(Historico historico) {
		this.id = historico.getId();
		this.data = historico.getData();
		this.valor = historico.getValor();
		this.conta = historico.getConta().getId();
	}
}
