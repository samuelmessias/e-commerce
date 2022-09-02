package com.example.ecommerce.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entities.Cupom;

public interface CupomRepository extends JpaRepository<Cupom, Long> {
	
	Optional<Cupom> findByCodigo(String codigo);

}
