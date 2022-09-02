package com.example.ecommerce.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.ecommerce.entities.Carrinho;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long> {

}
