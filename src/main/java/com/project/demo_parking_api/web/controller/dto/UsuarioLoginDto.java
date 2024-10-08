package com.project.demo_parking_api.web.controller.dto;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioLoginDto implements Serializable{


	private static final long serialVersionUID = 1L;

	@NotBlank
	@Email(message = "formato do e-mail está invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")	
	private String username;
	
	@NotBlank
	@Size(min = 6, max = 13)
	private String password;
	
	public UsuarioLoginDto(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	public UsuarioLoginDto() {
		super();
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	
}
