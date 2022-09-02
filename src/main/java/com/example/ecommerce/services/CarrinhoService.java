package com.example.ecommerce.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.ecommerce.dto.CarrinhoDTO;
import com.example.ecommerce.dto.InserirCupomDTO;
import com.example.ecommerce.entities.Carrinho;
import com.example.ecommerce.entities.Cupom;
import com.example.ecommerce.entities.ItemCarrinho;
import com.example.ecommerce.repositories.CarrinhoRepository;
import com.example.ecommerce.repositories.ItemCarrinhoRepository;
import com.example.ecommerce.services.exceptions.DatabaseException;
import com.example.ecommerce.services.exceptions.ResourceNotFoundException;

@Service
public class CarrinhoService {
	
	@Autowired
	private CarrinhoRepository CarrinhoRepository;
	
	@Autowired
	private ItemCarrinhoRepository itemCarrinhoRepository;
	
	@Autowired
	private CupomService cupomService;
	
	@Transactional(readOnly = true)
	public Page<CarrinhoDTO> findAll(Pageable pageable){
		Page<Carrinho> pages = CarrinhoRepository.findAll(pageable);
		return pages.map(x -> new CarrinhoDTO(x, itemCarrinhoRepository.findByCarrinho(x)));
	}
	
	@Transactional(readOnly = true)
	public CarrinhoDTO findById(Long id){		
		Carrinho entity = getEntity(id);		
		return new CarrinhoDTO(entity, itemCarrinhoRepository.findByCarrinho(entity));
	}
	
	@Transactional(readOnly = true)
	public Carrinho getEntity(Long id){		
		Optional<Carrinho> obj = CarrinhoRepository.findById(id);
		Carrinho entity = obj.orElseThrow(() ->  new ResourceNotFoundException("Carrinho não encontrado: " + id));
		return entity;
	}
	
	@Transactional
	public CarrinhoDTO createCarrinho(){		
		Carrinho entity = new Carrinho();
		entity.setTotalItem(0.0);
		entity.setSubTotalItem(0.0);
		entity.setDescontoItem(0.0);
		entity.setTotalCompra(0.0);
		entity.setSubTotalCompra(0.0);
		entity.setDescontoCompra(0.0);
		entity.setDescontoCupom(0.0);
		entity = CarrinhoRepository.save(entity);
		return new CarrinhoDTO(entity);
	}
	
	
	@Transactional
	public CarrinhoDTO adicionarCupom(Long id, InserirCupomDTO dto){		
		Carrinho entity = getEntity(id);
		
		Cupom newCupom = cupomService.findByCodigo(dto.getCodigo());
		
		if(entity.getCupom() == null || entity.getCupom().getDesconto() < newCupom.getDesconto()) {
			entity.setCupom(newCupom);
			atualizarCarrinho(entity);
			entity = CarrinhoRepository.save(entity);
		}		
		
		return new CarrinhoDTO(entity, itemCarrinhoRepository.findByCarrinho(entity));
	}
		
	
	public void delete(Long id) {
		try {
			CarrinhoRepository.deleteById(id);
		}
		catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException("Carrinho não encontrado " + id);
		}
		catch (DataIntegrityViolationException e) {
			throw new DatabaseException("Integridade do banco violada");
		}
	}
	
	
	private void aplicaDescontoCarrinho(Carrinho carrinho) {
		double desconto = 0;
		double descontoCupom = 0;
		
		if(carrinho.getTotalItem() >= 10000) {			
			desconto = carrinho.getTotalItem() * 10 / 100;
			
		}else if(carrinho.getTotalItem() >= 5000) {			
			desconto = carrinho.getTotalItem() * 7 / 100;
			
		}else if(carrinho.getTotalItem() >= 1000) {
			desconto = carrinho.getTotalItem() * 5 / 100;
		}
		
		if(carrinho.getCupom() != null) {
			descontoCupom  = carrinho.getTotalItem() * carrinho.getCupom().getDesconto() / 100;
		}
		
		carrinho.setSubTotalCompra(carrinho.getTotalItem());
		carrinho.setTotalCompra(carrinho.getTotalItem() - desconto - descontoCupom);
		carrinho.setDescontoCupom(descontoCupom);
		carrinho.setDescontoCompra(desconto);
		
	}
	
	
	public void atualizarCarrinho(Carrinho carrinho) {
		Double total = 0.0;
		Double subTotal = 0.0;
		Double desconto = 0.0;
		
		for(ItemCarrinho i : itemCarrinhoRepository.findByCarrinho(carrinho)) {
			total += i.getValorTotal();
			subTotal += i.getSubTotal();
			desconto += i.getDesconto();
		}
		
		carrinho.setTotalItem(total);
		carrinho.setSubTotalItem(subTotal);
		carrinho.setDescontoItem(desconto);
		aplicaDescontoCarrinho(carrinho);
		CarrinhoRepository.save(carrinho);
	}

}
