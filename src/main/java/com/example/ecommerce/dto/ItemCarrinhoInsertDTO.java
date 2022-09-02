package com.example.ecommerce.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.Data;

@Data
public class ItemCarrinhoInsertDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@NotNull(message = "Não deve ser nulo")	
	private Long carrinhoId;
	@NotNull(message = "Não deve ser nulo")
	private Long produtoId;
	@PositiveOrZero
	private Integer qto;

}
