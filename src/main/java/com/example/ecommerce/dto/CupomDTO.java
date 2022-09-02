package com.example.ecommerce.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.example.ecommerce.entities.Cupom;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CupomDTO implements Serializable{

	private static final long serialVersionUID = 1L;	
		
	private Long id;
	@NotBlank(message = "Campo de preenchimento obrigatório")			
	private String codigo;
	@NotBlank(message = "Campo de preenchimento obrigatório")
	private String descricao;
	@PositiveOrZero(message = "Deve ser maior ou igual a 0")
	@NotNull(message = "Não deve ser nulo")
	private Double desconto;
	
	public CupomDTO() {
		
	}
	
	public CupomDTO(Cupom entity) {
		id = entity.getId();
		codigo = entity.getCodigo();
		descricao = entity.getDescricao();
		desconto = entity.getDesconto();
	}

}
