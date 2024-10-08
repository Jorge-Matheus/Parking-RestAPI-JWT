package com.project.demo_parking_api.web.dto.mapper;

public class UsuarioResponseDto {
	private Long id;
	private String username;
	private String role;
	
	public UsuarioResponseDto() {

		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
