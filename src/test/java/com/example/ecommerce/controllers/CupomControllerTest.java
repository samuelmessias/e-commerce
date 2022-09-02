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

import com.example.ecommerce.dto.CupomDTO;
import com.example.ecommerce.factors.CupomFactory;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class CupomControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	private Long existingId;
	private Long noExistingId;
	private CupomDTO CupomDto;
	
	@BeforeEach
	void setUp()throws Exception {
		existingId = 1L;
		noExistingId = 1000L;
		
		CupomDto = CupomFactory.insertDTO();
	}
	
	@Test
	public void findAllShouldReturnSortedPageWhenSortByCodigo() throws Exception {
		ResultActions result = mockMvc.perform(get("/cupons?page=0&size=10&sort=codigo")			
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.content").exists());
		result.andExpect(jsonPath("$.content[0].codigo").value("INT516"));
		result.andExpect(jsonPath("$.content[1].codigo").value("LOJ101"));			
	}
	
	@Test
	public void findByIdShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {
		ResultActions result = mockMvc.perform(get("/cupons/{id}", noExistingId)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void findByIdShouldReturn200WhenIdExist() throws Exception {
		ResultActions result = mockMvc.perform(get("/cupons/{id}", existingId)				
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
		result.andExpect(jsonPath("$.id").value(1L));
		result.andExpect(jsonPath("$.codigo").value("LOJ101"));
	}	
	
	
	@Test
	public void insertShouldReturn422WhenCodigoBlank() throws Exception {
		CupomDto.setCodigo("");
		
		String jsonBody = objectMapper.writeValueAsString(CupomDto);
		
		ResultActions result = mockMvc.perform(post("/cupons")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("codigo"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo de preenchimento obrigatório"));
	}
	
	@Test
	public void insertShouldReturn422WhenDescricaoBlank() throws Exception {
		CupomDto.setDescricao("");
		
		String jsonBody = objectMapper.writeValueAsString(CupomDto);
		
		ResultActions result = mockMvc.perform(post("/cupons")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("descricao"));
		result.andExpect(jsonPath("$.errors[0].message").value("Campo de preenchimento obrigatório"));
	}
	
	@Test
	public void insertShouldReturn422WhenValorNull() throws Exception {
		CupomDto.setDesconto(null);
		
		String jsonBody = objectMapper.writeValueAsString(CupomDto);
		
		ResultActions result = mockMvc.perform(post("/cupons")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isUnprocessableEntity());
		result.andExpect(jsonPath("$.errors[0].fieldName").value("desconto"));
		result.andExpect(jsonPath("$.errors[0].message").value("Não deve ser nulo"));
	}
	
	
	
	@Test
	public void insertShouldReturn200WhenCreate() throws Exception {
		
		String jsonBody = objectMapper.writeValueAsString(CupomDto);
		
		ResultActions result = mockMvc.perform(post("/cupons")
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isCreated());
		result.andExpect(jsonPath("$.id").exists());
		result.andExpect(jsonPath("$.codigo").value("COD102"));
		result.andExpect(jsonPath("$.descricao").value("teste"));
		result.andExpect(jsonPath("$.desconto").value(10.0));
	}
	
	
	@Test
	public void updateDadoPessoalShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {		
		String jsonBody = objectMapper.writeValueAsString(CupomDto);
		
		ResultActions result = mockMvc.perform(put("/cupons/{id}", noExistingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}
	
	@Test
	public void updateDadoPessoalShouldReturn200WhenIdExist() throws Exception {		
		String jsonBody = objectMapper.writeValueAsString(CupomDto);
		
		ResultActions result = mockMvc.perform(put("/cupons/{id}", existingId)
				.content(jsonBody)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isOk());
	}
	
	
	@Test
	public void deleteShouldReturnNoContentWhenIdExists() throws Exception {		
		ResultActions result = 
				mockMvc.perform(delete("/cupons/{id}", existingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNoContent());
	}
	
	@Test
	public void deleteShouldReturnNotFoundWhenIdDoesNotExist() throws Exception {		
		ResultActions result = 
				mockMvc.perform(delete("/cupons/{id}", noExistingId)
					.accept(MediaType.APPLICATION_JSON));
		
		result.andExpect(status().isNotFound());
	}

}
