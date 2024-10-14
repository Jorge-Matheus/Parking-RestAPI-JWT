package com.project.demo_parking_api.web.dto;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ClienteCreateDto {

	@NotBlank
	@Size(min = 5, max = 100)
	private String nome;
	
	
	@Size(min = 11, max = 11)
	@CPF
	private String cpf;
	
	
	public ClienteCreateDto() {}

	
	public ClienteCreateDto(@NotBlank @Size(min = 5, max = 100) String nome,
			@Size(min = 11, max = 11) @CPF String cpf) {
		super();
		this.nome = nome;
		this.cpf = cpf;
	}

	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}