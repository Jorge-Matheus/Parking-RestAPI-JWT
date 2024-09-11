package com.project.demo_parking_api.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;


@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
	static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "username", unique = true, nullable = false, length = 100)
	private String username;
	
	@Column(name = "password", nullable = false, length = 200)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, length = 25)
	private Role role;
	
	@Column(name = "data_criacao")
	private LocalDateTime dataCriacao;
	
	@Column(name = "data_modificacao")
	private LocalDateTime dataModificacao;
	
	@Column(name = "criado_por")
	private String criadoPor;
	
	@Column(name = "modificado_por")
	private String modificadoPor;
	
	
	
	public enum Role {
		ROLE_ADMIN, ROLE_CLIENTE
	}


	
	public Usuario() {}
	
	

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



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public Role getRole() {
		return role;
	}



	public void setRole(Role role) {
		this.role = role;
	}



	public LocalDateTime getDataCriacao() {
		return dataCriacao;
	}



	public void setDataCriacao(LocalDateTime dataCriacao) {
		this.dataCriacao = dataCriacao;
	}



	public LocalDateTime getDataModificacao() {
		return dataModificacao;
	}



	public void setDataModificacao(LocalDateTime dataModificacao) {
		this.dataModificacao = dataModificacao;
	}



	public String getCriadoPor() {
		return criadoPor;
	}



	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}



	public String getModificadoPor() {
		return modificadoPor;
	}



	public void setModificadoPor(String modificadoPor) {
		this.modificadoPor = modificadoPor;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Usuario [id=" + id + "]";
	}
}
