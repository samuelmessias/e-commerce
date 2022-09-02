package com.example.ecommerce.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.ecommerce.entities.Carrinho;
import com.example.ecommerce.entities.ItemCarrinho;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarrinhoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Long id;	
	private Double subTotalItem;
	private Double totalItem;
	private Double descontoItem;	
	
	private List<ItemCarrinhoDTO> itens = new ArrayList<>();
	private CupomDTO cupom;
	
	private Double subTotalCompra;	
	private Double descontoCompra;
	private Double descontoCupom;
	private Double totalCompra;	
	
	public CarrinhoDTO(Carrinho entity) {
		id = entity.getId();
		totalItem = entity.getTotalItem();
		subTotalItem = entity.getSubTotalItem();
		descontoItem = entity.getDescontoItem();
		
		if(entity.getCupom() != null) {
			cupom = new CupomDTO(entity.getCupom());
		}
			
		subTotalCompra = entity.getSubTotalCompra();	
		descontoCompra = entity.getDescontoCompra();
		totalCompra = entity.getTotalCompra();
		descontoCupom = entity.getDescontoCupom();
		
	}
	
	public CarrinhoDTO(Carrinho entity, List<ItemCarrinho> list) {
		this(entity);
		list.forEach(item -> this.itens.add(new ItemCarrinhoDTO(item)));		
	}
	
	

}
