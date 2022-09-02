package com.example.ecommerce.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InserirCupomDTO implements Serializable{

	private static final long serialVersionUID = 1L;		
	
	@NotBlank(message = "Campo de preenchimento obrigatório")		
	private String codigo;	
	
	public InserirCupomDTO() {
		
	}
	

}
