package com.example.ecommerce.factors;

import com.example.ecommerce.dto.ProdutoDTO;
import com.example.ecommerce.entities.enums.Categoria;

public class ProdutoFactory {

	public static ProdutoDTO insertDTO() {
		ProdutoDTO dto = new ProdutoDTO();
		dto.setId(null);
		dto.setNome("PC Gamer");
		dto.setDescricao("PC Gamer");
		dto.setCategoria(Categoria.INFORMATICA);
		dto.setValor(2000.0);
		return dto;
	}
}
