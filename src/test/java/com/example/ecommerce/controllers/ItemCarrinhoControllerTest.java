package com.example.ecommerce.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.ItemCarrinhoInsertDTO;
import com.example.ecommerce.factors.ItemCarrinhoFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ItemCarrinhoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;	
	
	private ItemCarrinhoInsertDTO itemCarrinhoDTO;
	
	@BeforeEach
	void setUp()throws Exception {		
		itemCarrinhoDTO = ItemCarrinhoFactory.insertDTO();
	}	
	
	
	@Test
	public void insertShouldReturn422WhenCarrinhoIdNull() throws Exception {
		itemCarrinhoDTO.setCarrinhoId(null);
		
		String jsonBody = objectMapper.writeValueAsString(itemCarrinhoDTO);
		
		ResultActions result = mockMvc.perform(post("/itens")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("carrinhoId"));
		result.andExpect(jsonPath("$.errors[0].message").value("Não deve ser nulo"));
	}
	
	
	@Test
	public void insertShouldReturn404WhenCarrinhoIdNotFound() throws Exception {
		itemCarrinhoDTO.setCarrinhoId(100L);
		
		String jsonBody = objectMapper.writeValueAsString(itemCarrinhoDTO);
		
		ResultActions result = mockMvc.perform(post("/itens")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	
	
	@Test
	public void insertShouldReturn422WhenProdutoIdNull() throws Exception {
		itemCarrinhoDTO.setProdutoId(null);
		
		String jsonBody = objectMapper.writeValueAsString(itemCarrinhoDTO);
		
		ResultActions result = mockMvc.perform(post("/itens")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("produtoId"));
		result.andExpect(jsonPath("$.errors[0].message").value("Não deve ser nulo"));
	}
	
	@Test
	public void insertShouldReturn404WhenProdutoIdNotFound() throws Exception {
		itemCarrinhoDTO.setProdutoId(100L);
		
		String jsonBody = objectMapper.writeValueAsString(itemCarrinhoDTO);
		
		ResultActions result = mockMvc.perform(post("/itens")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	
	
	@Test
	public void insertShouldDiscountApplicationWhenQuantityGreater10() throws Exception {		
		itemCarrinhoDTO.setQto(10);
		
		String jsonBody = objectMapper.writeValueAsString(itemCarrinhoDTO);
		
		ResultActions result = mockMvc.perform(post("/itens")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.valorUnitario").value(1000L));
		result.andExpect(jsonPath("$.subTotal").value(10000L));
		result.andExpect(jsonPath("$.desconto").value(1000L));
		result.andExpect(jsonPath("$.valorTotal").value(9000L));
	}
	
	@Test
	public void insertShouldNotDiscountApplicationWhenQuantityLess10() throws Exception {		
		itemCarrinhoDTO.setQto(1);
		
		String jsonBody = objectMapper.writeValueAsString(itemCarrinhoDTO);
		
		ResultActions result = mockMvc.perform(post("/itens")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.valorUnitario").value(1000L));
		result.andExpect(jsonPath("$.subTotal").value(1000L));
		result.andExpect(jsonPath("$.desconto").value(0L));
		result.andExpect(jsonPath("$.valorTotal").value(1000L));
	}
	
	
	

}
