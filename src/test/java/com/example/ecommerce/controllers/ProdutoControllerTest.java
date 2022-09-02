package com.example.ecommerce.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

import com.example.ecommerce.dto.ProdutoDTO;
import com.example.ecommerce.factors.ProdutoFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProdutoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private Long noExistingId;
	private ProdutoDTO produtoDto;
	
	@BeforeEach
	void setUp()throws Exception {
		existingId = 1L;
		noExistingId = 1000L;
		
		produtoDto = ProdutoFactory.insertDTO();
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByNome() throws Exception {
		ResultActions result = mockMvc.perform(get("/produtos?page=0&size=10&sort=nome")			
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].nome").value("Cartucho HP"));
		result.andExpect(jsonPath("$.content[1].nome").value("Lavadora de Roupas"));
		result.andExpect(jsonPath("$.content[2].nome").value("Notebook Acer"));		
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		ResultActions result = mockMvc.perform(get("/produtos/{id}", noExistingId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturn200WhenIdExist() throws Exception {
		ResultActions result = mockMvc.perform(get("/produtos/{id}", existingId)				
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.nome").value("Notebook Acer"));
	}	
	
	
	@Test
	public void insertShouldReturn422WhenNomeBlank() throws Exception {
		produtoDto.setNome("");
		
		String jsonBody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(post("/produtos")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("nome"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo de preenchimento obrigatório"));
	}
	
	@Test
	public void insertShouldReturn422WhenDescricaoBlank() throws Exception {
		produtoDto.setDescricao("");
		
		String jsonBody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(post("/produtos")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("descricao"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo de preenchimento obrigatório"));
	}
	
	@Test
	public void insertShouldReturn422WhenValorNull() throws Exception {
		produtoDto.setValor(null);
		
		String jsonBody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(post("/produtos")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("valor"));
		result.andExpect(jsonPath("$.errors[0].message").value("Não deve ser nulo"));
	}
	
	@Test
	public void insertShouldReturn422WhenValorMenorZero() throws Exception {
		produtoDto.setValor(-10.0);
		
		String jsonBody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(post("/produtos")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("valor"));
		result.andExpect(jsonPath("$.errors[0].message").value("Deve ser maior ou igual a 0"));
	}
	
	@Test
	public void insertShouldReturn200WhenCreate() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(post("/produtos")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.nome").value("PC Gamer"));
		result.andExpect(jsonPath("$.descricao").value("PC Gamer"));
		result.andExpect(jsonPath("$.valor").value(2000.0));
		result.andExpect(jsonPath("$.categoria").value("INFORMATICA"));
	}
	
	
	@Test
	public void updateDadoPessoalShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {		
		String jsonBody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(put("/produtos/{id}", noExistingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateDadoPessoalShouldReturn200WhenIdExist() throws Exception {		
		String jsonBody = objectMapper.writeValueAsString(produtoDto);
		
		ResultActions result = mockMvc.perform(put("/produtos/{id}", existingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {		
		ResultActions result = 
				mockMvc.perform(delete("/produtos/{id}", existingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {		
		ResultActions result = 
				mockMvc.perform(delete("/produtos/{id}", noExistingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}

}
