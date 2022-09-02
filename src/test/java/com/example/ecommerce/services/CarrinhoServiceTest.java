package com.example.ecommerce.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.ecommerce.repositories.CarrinhoRepository;

@ExtendWith(SpringExtension.class)
public class CarrinhoServiceTest {
	
	@InjectMocks
	private CarrinhoService service;
	
	@Mock
	private CarrinhoRepository repository;
	
	
	@BeforeEach
	void setup() throws Exception {
		
	}
	
	
	
}
