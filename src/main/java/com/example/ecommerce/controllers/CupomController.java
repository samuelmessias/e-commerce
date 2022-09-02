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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.ecommerce.dto.CupomDTO;
import com.example.ecommerce.services.CupomService;

@RestController
@RequestMapping(value = "/cupons")
public class CupomController {
	
	@Autowired
	private CupomService service;
	
	@GetMapping
	public ResponseEntity<Page<CupomDTO>> findAll(Pageable pageable){
		Page<CupomDTO> pages = service.findAll(pageable);
		return ResponseEntity.ok().body(pages);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<CupomDTO> findById(@PathVariable Long id){
		CupomDTO dto  = service.findById(id);
		return ResponseEntity.ok().body(dto);
	}
	
	@PostMapping
	public ResponseEntity<CupomDTO> inserir(@Valid @RequestBody CupomDTO dto){
		dto = service.insert(dto);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();
		return ResponseEntity.created(uri).body(dto);
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<CupomDTO> update(@PathVariable Long id, @Valid @RequestBody CupomDTO dto){
		dto = service.update(id, dto);
		return ResponseEntity.ok().body(dto);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		service.delete(id);
		return ResponseEntity.noContent().build();
	}

}
