package com.example.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.CupomDTO;
import com.example.ecommerce.entities.Cupom;
import com.example.ecommerce.repositories.CupomRepository;
import com.example.ecommerce.services.exceptions.DatabaseException;
import com.example.ecommerce.services.exceptions.ResourceNotFoundException;

@Service
public class CupomService {
	
	@Autowired
	private CupomRepository cupomRepository;
	
	@Transactional(readOnly = true)
	public Page<CupomDTO> findAll(Pageable pageable){
		Page<Cupom> pages = cupomRepository.findAll(pageable);
		return pages.map(x -> new CupomDTO(x));
	}
	
	@Transactional(readOnly = true)
	public CupomDTO findById(Long id){		
		Cupom entity = getEntity(id);
		return new CupomDTO(entity);
	}
	
	@Transactional(readOnly = true)
	public Cupom getEntity(Long id){		
		Optional<Cupom> obj = cupomRepository.findById(id);
		Cupom entity = obj.orElseThrow(() ->  new ResourceNotFoundException("Cupom não encontrado: " + id));
		return entity;
	}
	
	@Transactional(readOnly = true)
	public Cupom findByCodigo(String codigo){		
		Optional<Cupom> obj = cupomRepository.findByCodigo(codigo);
		Cupom entity = obj.orElseThrow(() ->  new ResourceNotFoundException("Cupom não encontrado: " + codigo));
		return entity;
	}
	
	@Transactional
	public CupomDTO insert(CupomDTO dto){		
		Cupom entity = new Cupom();
		copyDtoToEntity(dto, entity);
		entity = cupomRepository.save(entity);
		return new CupomDTO(entity);
	}
	
	@Transactional
	public CupomDTO update(Long id, CupomDTO dto) {		
		Cupom entity = getEntity(id);
		copyDtoToEntity(dto, entity);
		entity = cupomRepository.save(entity);
		return new CupomDTO(entity);		
	}
	
	public void delete(Long id) {
		try {
			cupomRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Cupom não encontrado " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integridade do banco violada");
		}
	}	
	
	private void copyDtoToEntity(CupomDTO dto, Cupom entity) {
		entity.setCodigo(dto.getCodigo());
		entity.setDescricao(dto.getDescricao());
		entity.setDesconto(dto.getDesconto());
	}	

}
