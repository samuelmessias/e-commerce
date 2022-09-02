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

import com.example.ecommerce.dto.InserirCupomDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CarrinhoControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private String codigoCupomExisting;
	private String codigoCupomNoExisting;
	
	
	private InserirCupomDTO inserirCupomDTO;
	
	@BeforeEach
	void setUp()throws Exception {
		existingId = 1L;
		codigoCupomExisting = "LOJ101";
		codigoCupomNoExisting = "NOT124";
		
		inserirCupomDTO = new InserirCupomDTO();		
		
	}	
	
	
	@Test
	public void createCarrinhoShouldReturn201() throws Exception {		
		
		ResultActions result = mockMvc.perform(post("/carrinhos")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
	}
	
	
	@Test
	public void adicionarCupomShouldReturn404WhenCupomNotFound() throws Exception {
		inserirCupomDTO.setCodigo(codigoCupomNoExisting);
		
		String jsonBody = objectMapper.writeValueAsString(inserirCupomDTO);
		
		ResultActions result = mockMvc.perform(post("/carrinhos/{id}/cupom", existingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	
	@Test
	public void adicionarCupomShouldReturn422WhenCupomBlank() throws Exception {
		inserirCupomDTO.setCodigo("");
		
		String jsonBody = objectMapper.writeValueAsString(inserirCupomDTO);
		
		ResultActions result = mockMvc.perform(post("/carrinhos/{id}/cupom", existingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("codigo"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo de preenchimento obrigatório"));
	}
	
	
	@Test
	public void adicionarCupomShouldReturn201() throws Exception {
		inserirCupomDTO.setCodigo(codigoCupomExisting);
		
		String jsonBody = objectMapper.writeValueAsString(inserirCupomDTO);
		
		ResultActions result = mockMvc.perform(post("/carrinhos/{id}/cupom", existingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
	}
	

}
