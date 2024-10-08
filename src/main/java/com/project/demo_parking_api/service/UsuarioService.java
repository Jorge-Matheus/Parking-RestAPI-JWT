package com.project.demo_parking_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo_parking_api.entity.Usuario;
import com.project.demo_parking_api.entity.Usuario.Role;
import com.project.demo_parking_api.exception.EntityNotFoundException;
import com.project.demo_parking_api.exception.PasswordInvalidException;
import com.project.demo_parking_api.exception.UsernameUniqueViolationException;
import com.project.demo_parking_api.repositories.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Transactional
	public Usuario salvar(Usuario usuario)  {
		try {
			usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
			return usuarioRepository.save(usuario);
		}
		catch(org.springframework.dao.DataIntegrityViolationException ex) {
			throw new UsernameUniqueViolationException(String.format("Username {%s} já cadastrado", usuario.getUsername()));
		}
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(String.format("Usuario id=%s não encontado", id)));
	}

	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		Usuario usuario = usuarioRepository.findById(id).get();
		
		if(!passwordEncoder.matches(senhaAtual, usuario.getPassword())) {
			throw new PasswordInvalidException("Senha atual invalida, verifique e tente novamente");
		}
		if(!novaSenha.equals(confirmaSenha)) {
			throw new PasswordInvalidException("Senha de confirmaçao nao confere com a nova senha");
		}
		usuario.setPassword(passwordEncoder.encode(novaSenha));
		
		return usuarioRepository.save(usuario);
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorUsername(String username) {
		return usuarioRepository.findByUsername(username)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Usuario com '%s' não encontrado", username)));
	}

	
	@Transactional(readOnly = true)
	public Usuario.Role buscarRolePorUsername(String username) {
		return usuarioRepository.findRoleByUsername(username);
	}	
}
