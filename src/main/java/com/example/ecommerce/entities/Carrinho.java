package com.example.ecommerce.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_carrinho")
public class Carrinho implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;	
	
	@Column(columnDefinition = "DOUBLE DEFAULT 0")
	private Double totalItem;
	@Column(columnDefinition = "DOUBLE DEFAULT 0")
	private Double subTotalItem;
	@Column(columnDefinition = "DOUBLE DEFAULT 0")
	private Double descontoItem;
			
	
	@Column(columnDefinition = "DOUBLE DEFAULT 0")
	private Double totalCompra;
	@Column(columnDefinition = "DOUBLE DEFAULT 0")
	private Double subTotalCompra;
	@Column(columnDefinition = "DOUBLE DEFAULT 0")
	private Double descontoCompra;
	
	@Column(columnDefinition = "DOUBLE DEFAULT 0")
	private Double descontoCupom;
	
	private Cupom cupom;
	
	@OneToMany(mappedBy = "carrinho")
	private Set<ItemCarrinho> itens = new HashSet<>();		

}
