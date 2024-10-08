package com.project.demo_parking_api.web.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UsuarioCreateDto  {
	

	@NotBlank
	@Email(message = "formato do e-mail está invalido", regexp = "^[a-z0-9.+-]+@[a-z0-9.-]+\\.[a-z]{2,}$")	
	private String username;
	
	@NotBlank
	@Size(min = 6, max = 13)
	private String password;
	
	public UsuarioCreateDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
	

	public UsuarioCreateDto() {
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
