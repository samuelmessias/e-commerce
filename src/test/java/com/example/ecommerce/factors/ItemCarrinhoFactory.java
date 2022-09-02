package com.example.ecommerce.factors;

import com.example.ecommerce.dto.ItemCarrinhoInsertDTO;

public class ItemCarrinhoFactory {

	public static ItemCarrinhoInsertDTO insertDTO() {
		ItemCarrinhoInsertDTO dto = new ItemCarrinhoInsertDTO();		
		dto.setCarrinhoId(1L);
		dto.setProdutoId(1L);		
		dto.setQto(1);
		return dto;
	}
}
