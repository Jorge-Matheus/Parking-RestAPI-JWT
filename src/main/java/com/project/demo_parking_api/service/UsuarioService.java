package com.project.demo_parking_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo_parking_api.entity.Usuario;
import com.project.demo_parking_api.exception.EntityNotFoundException;
import com.project.demo_parking_api.exception.PasswordInvalidException;
import com.project.demo_parking_api.exception.UsernameUniqueViolationException;
import com.project.demo_parking_api.repositories.UsuarioRepository;


@Service
public class UsuarioService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Transactional
	public Usuario salvar(Usuario usuario)  {
		try {
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
		
		try {
			if(!novaSenha.equals(confirmaSenha)) {
				throw new PasswordInvalidException("Nova senha não confere com confirmação de senha.");
			}
			
			Usuario user = buscarPorId(id);
			if(!user.getPassword().equals(senhaAtual)) {
				throw new PasswordInvalidException("Sua senha não confere");
			}
			
			user.setPassword(novaSenha);
			return user;
		}
		catch(PasswordInvalidException ex) {
			throw new PasswordInvalidException("Password invalid");
		}
	}

	@Transactional(readOnly = true)
	public List<Usuario> buscarTodos() {
		return usuarioRepository.findAll();
	}
	
	
	
}
