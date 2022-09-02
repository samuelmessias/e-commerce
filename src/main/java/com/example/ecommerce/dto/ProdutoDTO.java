package com.example.ecommerce.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.example.ecommerce.entities.Produto;
import com.example.ecommerce.entities.enums.Categoria;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProdutoDTO implements Serializable{

	private static final long serialVersionUID = 1L;	
	
	private Long id;
	@NotBlank(message = "Campo de preenchimento obrigatório")	
	private String nome;	
	@NotBlank(message = "Campo de preenchimento obrigatório")
	private String descricao;
	@PositiveOrZero(message = "Deve ser maior ou igual a 0")
	@NotNull(message = "Não deve ser nulo")
	private Double valor;	
	private Categoria categoria;
	
	public ProdutoDTO() {
		
	}
	
	public ProdutoDTO(Produto entity) {
		id = entity.getId();
		nome = entity.getNome();
		descricao = entity.getDescricao();
		valor = entity.getValor();
		categoria = entity.getCategoria();
	}

}
