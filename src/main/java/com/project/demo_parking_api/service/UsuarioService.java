package com.project.demo_parking_api.service;

import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo_parking_api.entity.Usuario;
import com.project.demo_parking_api.repositories.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario salvar(Usuario usuario) {
		return usuarioRepository.save(usuario);
	}

	@Transactional(readOnly = true)
	public Usuario buscarPorId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found!"));
	}

	@Transactional
	public Usuario editarSenha(Long id, String senhaAtual, String novaSenha, String confirmaSenha) {
		if(!novaSenha.equals(confirmaSenha)) {
			throw new RuntimeException("Nova senha não confere com confirmação de senha.");
		}
		
		Usuario user = buscarPorId(id);
		if(!user.getPassword().equals(senhaAtual)) {
			throw new RuntimeException("Sua senha não confere");
		}
		
		user.setPassword(novaSenha);
		return user;
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}
	
	
	
}
