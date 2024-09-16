package com.project.demo_parking_api.web.dto;

public class UsuarioCreateDto {

	private String username;
	
	private String password;
	
	
	public UsuarioCreateDto() {}
	
	public UsuarioCreateDto(String username, String password) {
		super();
		this.username = username;
		this.password = password;
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

	@Override
	public String toString() {
		return "UsuarioCreateDto [username=" + username + ", password=" + password + "]";
	}
	
	
}