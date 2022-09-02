package com.example.ecommerce.controllers;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.ecommerce.dto.CarrinhoDTO;
import com.example.ecommerce.dto.InserirCupomDTO;
import com.example.ecommerce.services.CarrinhoService;

@RestController
@RequestMapping(value = "/carrinhos")
public class CarrinhoController {
	
	@Autowired
	private CarrinhoService service;
	
	@GetMapping
	public ResponseEntity<Page<CarrinhoDTO>> findAll(Pageable pageable){
		Page<CarrinhoDTO> pages = service.findAll(pageable);
		return ResponseEntity.ok().body(pages);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CarrinhoDTO> findById(@PathVariable Long id){
		CarrinhoDTO dto  = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<CarrinhoDTO> createCarrinho(){
		CarrinhoDTO dto = service.createCarrinho();
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PostMapping(value = "/{id}/cupom")
	public ResponseEntity<CarrinhoDTO> adicionarCupom(@PathVariable Long id, @Valid @RequestBody InserirCupomDTO dto){
		CarrinhoDTO carrinhoDto = service.adicionarCupom(id, dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(carrinhoDto.getId()).toUri();
		return ResponseEntity.created(uri).body(carrinhoDto);
	}
	
		
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
