package com.example.ecommerce.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tb_item_carrinho")
public class ItemCarrinho implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "carrinho_id")
	private Carrinho carrinho;
	
	@ManyToOne
	@JoinColumn(name = "produto_id")
	private Produto produto;
	private Integer qto;
	private Double valorUnitario;
	private Double subTotal;
	private Double desconto;
	private Double valorTotal;

}
