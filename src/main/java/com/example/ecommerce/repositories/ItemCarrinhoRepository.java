package com.example.ecommerce.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entities.Carrinho;
import com.example.ecommerce.entities.ItemCarrinho;
import com.example.ecommerce.entities.Produto;

public interface ItemCarrinhoRepository extends JpaRepository<ItemCarrinho, Long> {
	List<ItemCarrinho> findByCarrinho(Carrinho carrinho);
	
	ItemCarrinho findByCarrinhoAndProduto(Carrinho carrinho, Produto produto);
}
