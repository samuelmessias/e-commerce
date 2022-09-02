package com.example.ecommerce.factors;

import com.example.ecommerce.dto.CupomDTO;

public class CupomFactory {

	public static CupomDTO insertDTO() {
		CupomDTO dto = new CupomDTO();
		dto.setId(null);
		dto.setCodigo("COD102");
		dto.setDescricao("teste");		
		dto.setDesconto(10.0);
		return dto;
	}
}
