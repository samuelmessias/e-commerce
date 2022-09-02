package com.example.ecommerce.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.ecommerce.dto.ItemCarrinhoDTO;
import com.example.ecommerce.dto.ItemCarrinhoInsertDTO;
import com.example.ecommerce.entities.ItemCarrinho;
import com.example.ecommerce.repositories.ItemCarrinhoRepository;

@ExtendWith(SpringExtension.class)
public class ItemCarrinhoServiceTest {
	
	@InjectMocks
	private ItemCarrinhoService itemCarrinhoService;
	
	@Mock
	private ItemCarrinhoRepository itemCarrinhoRepository;
	
	@Mock
	private CarrinhoService carrinhoService;
	
	@Mock
	private ProdutoService produtoService;
	
	private long carrinhoExistingId;
	private long produtoExistingId;
	
	private ItemCarrinhoInsertDTO dtoInsert;
	
	private ItemCarrinho itemCarrinho;
	
		
	@BeforeEach
	void setup() throws Exception {
		carrinhoExistingId = 1L;
		produtoExistingId = 1L;
		
		dtoInsert = new ItemCarrinhoInsertDTO();
		dtoInsert.setCarrinhoId(carrinhoExistingId);
		dtoInsert.setProdutoId(produtoExistingId);
		
		Mockito.when(itemCarrinhoRepository.save(ArgumentMatchers.any())).thenReturn(itemCarrinho);	
	}
	
	@Test
	public void insertDeveriaAplicarDescontoQuandoQtoItemMaiorQue10() {		
		dtoInsert.setQto(10);
		
		ItemCarrinhoDTO dto = itemCarrinhoService.insert(dtoInsert);
		
		//Assertions.assertEquals(null, null);
		//Mockito.verify(itemCarrinhoRepository).save(dtoInsert);
	}

}
