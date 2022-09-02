package com.example.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.ProdutoDTO;
import com.example.ecommerce.entities.Produto;
import com.example.ecommerce.repositories.ProdutoRepository;
import com.example.ecommerce.services.exceptions.DatabaseException;
import com.example.ecommerce.services.exceptions.ResourceNotFoundException;

@Service
public class ProdutoService {
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Transactional(readOnly = true)
	public Page<ProdutoDTO> findAll(Pageable pageable){
		Page<Produto> pages = produtoRepository.findAll(pageable);
		return pages.map(x -> new ProdutoDTO(x));
	}
	
	@Transactional(readOnly = true)
	public ProdutoDTO findById(Long id){		
		Produto entity = getEntity(id);
		return new ProdutoDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Produto getEntity(Long id){		
		Optional<Produto> obj = produtoRepository.findById(id);
		Produto entity = obj.orElseThrow(() ->  new ResourceNotFoundException("Produto não encontrado: " + id));
		return entity;
	}
	
	@Transactional
	public ProdutoDTO insert(ProdutoDTO dto){		
		Produto entity = new Produto();
		copyDtoToEntity(dto, entity);
		entity = produtoRepository.save(entity);
		return new ProdutoDTO(entity);
	}
	
	@Transactional
	public ProdutoDTO update(Long id, ProdutoDTO dto) {		
		Produto entity = getEntity(id);
		copyDtoToEntity(dto, entity);
		entity = produtoRepository.save(entity);
		return new ProdutoDTO(entity);		
	}
	
	public void delete(Long id) {
		try {
			produtoRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Produto não encontrado " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integridade do banco violada");
		}
	}	
	
	private void copyDtoToEntity(ProdutoDTO dto, Produto entity) {
		entity.setNome(dto.getNome());
		entity.setDescricao(dto.getDescricao());
		entity.setCategoria(dto.getCategoria());
		entity.setValor(dto.getValor());
	}	

}
