package com.example.ecommerce.entities;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.ecommerce.entities.enums.Categoria;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_produto")
public class Produto implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(nullable = false, length = 50)
	private String nome;	
	@Column(columnDefinition="TEXT")
	private String descricao;	
	private Double valor;
	@Enumerated(EnumType.STRING)
	private Categoria categoria;

}
