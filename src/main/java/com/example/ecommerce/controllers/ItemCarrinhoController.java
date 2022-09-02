package com.example.ecommerce.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.ecommerce.dto.ItemCarrinhoDTO;
import com.example.ecommerce.dto.ItemCarrinhoInsertDTO;
import com.example.ecommerce.services.ItemCarrinhoService;

@RestController
@RequestMapping(value = "/itens")
public class ItemCarrinhoController {
	
	@Autowired
	private ItemCarrinhoService service;	
	
	@PostMapping
	public ResponseEntity<ItemCarrinhoDTO> inserir(@Valid @RequestBody ItemCarrinhoInsertDTO dto){
		ItemCarrinhoDTO newDto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newDto.getId()).toUri();
		return ResponseEntity.created(uri).body(newDto);
	}


}
