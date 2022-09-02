package com.example.ecommerce.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.ItemCarrinhoDTO;
import com.example.ecommerce.dto.ItemCarrinhoInsertDTO;
import com.example.ecommerce.entities.Carrinho;
import com.example.ecommerce.entities.ItemCarrinho;
import com.example.ecommerce.entities.Produto;
import com.example.ecommerce.repositories.ItemCarrinhoRepository;

@Service
public class ItemCarrinhoService {
	
	@Autowired
	private ItemCarrinhoRepository repository;
	
	@Autowired
	private CarrinhoService carrinhoService;
	
	@Autowired
	private ProdutoService produtoService;	
	
	@Transactional
	public ItemCarrinhoDTO insert(ItemCarrinhoInsertDTO dto){
		Carrinho carrinho = carrinhoService.getEntity(dto.getCarrinhoId());
		Produto produto = produtoService.getEntity(dto.getProdutoId());
		
		ItemCarrinho item = repository.findByCarrinhoAndProduto(carrinho, produto);
		if(item != null) {
			item.setQto(item.getQto() + dto.getQto());
			calcularDesconto(item);
			item = repository.save(item);
			
		}else {		
			item = new ItemCarrinho();
			copyDtoToEntity(dto, item);
			calcularDesconto(item);
			item = repository.save(item);			
		}
		
		carrinhoService.atualizarCarrinho(carrinho);
		return new ItemCarrinhoDTO(item);
	}	

	private void copyDtoToEntity(ItemCarrinhoInsertDTO dto, ItemCarrinho entity) {
		Produto produto = produtoService.getEntity(dto.getProdutoId());
		entity.setCarrinho(carrinhoService.getEntity(dto.getCarrinhoId()));
		entity.setProduto(produto);		
		entity.setQto(dto.getQto());
		entity.setValorUnitario(produto.getValor());		
		
	}
	
	private void calcularDesconto(ItemCarrinho item) {
		if(item.getQto() >= 10) {
			Double total = item.getValorUnitario()* item.getQto();
			Double desconto = (total *10) /100;
			
			item.setSubTotal(total);
			item.setValorTotal(total-desconto);
			item.setDesconto(desconto);
			
		}else {
			item.setSubTotal(item.getValorUnitario()* item.getQto());
			item.setValorTotal(item.getSubTotal());
			item.setDesconto(0.0);
		}
	}

}
