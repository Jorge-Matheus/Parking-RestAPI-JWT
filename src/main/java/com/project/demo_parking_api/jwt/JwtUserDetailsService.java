package com.project.demo_parking_api.jwt;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.demo_parking_api.entity.Usuario;
import com.project.demo_parking_api.service.UsuarioService;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	private final UsuarioService usuarioService;
	
	
	public JwtUserDetailsService(UsuarioService usuarioService) {
		super();
		this.usuarioService = usuarioService;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = usuarioService.buscarPorUsername(username);
		return new JwtUserDetails(usuario);
	}

	
	public JwtToken getTokenAuthenticated(String username) {
		Usuario.Role role = usuarioService.buscarRolePorUsername(username);
		return JwtUtils.createToken(username, role.name().substring("ROLE_".length()));
	}
	
}
