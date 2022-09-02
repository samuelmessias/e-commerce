package com.example.ecommerce.dto;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InserirCupomDTO implements Serializable{

	private static final long serialVersionUID = 1L;		
	
	@NotBlank
	@Size(min = 4, max = 20)		
	private String codigo;	
	
	public InserirCupomDTO() {
		
	}
	

}
