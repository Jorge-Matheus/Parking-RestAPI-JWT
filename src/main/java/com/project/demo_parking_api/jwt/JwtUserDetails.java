package com.project.demo_parking_api.jwt;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import com.project.demo_parking_api.entity.Usuario;

public class JwtUserDetails extends User {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private Usuario usuario;
	
	public JwtUserDetails(Usuario usuario) {
		super(usuario.getUsername(), usuario.getPassword(), AuthorityUtils.createAuthorityList(usuario.getRole().name()));
		this.usuario = usuario;
	}

	public Long getId() {
		return usuario.getId(); 
	}
	

	public String getRole() {
		return usuario.getRole().name();
	}

}
