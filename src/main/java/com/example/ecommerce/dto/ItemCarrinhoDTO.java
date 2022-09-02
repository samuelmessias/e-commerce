package com.example.ecommerce.dto;

import java.io.Serializable;

import com.example.ecommerce.entities.ItemCarrinho;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemCarrinhoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;	
	private String produto;
	private Integer qto;
	private Double valorUnitario;
	private Double subTotal;
	private Double desconto;
	private Double valorTotal;
	
	public ItemCarrinhoDTO(ItemCarrinho entity) {
		id = entity.getId();		
		produto = entity.getProduto().getId() + " - " + entity.getProduto().getNome();
		qto = entity.getQto();
		valorUnitario = entity.getValorUnitario();
		subTotal =  entity.getSubTotal();
		desconto = entity.getDesconto();
		valorTotal = entity.getValorTotal();
	}

}
