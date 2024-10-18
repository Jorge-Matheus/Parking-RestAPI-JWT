package com.project.demo_parking_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo_parking_api.entity.Cliente;
import com.project.demo_parking_api.exception.CpfUniqueViolationException;
import com.project.demo_parking_api.exception.EntityNotFoundException;
import com.project.demo_parking_api.repositories.ClienteRepository;
import com.project.demo_parking_api.repositories.projection.ClienteProjection;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository clienteRepository;
	
	
	public ClienteService() {}
	
	
	@Transactional
	public Cliente salvar(Cliente cliente) {
		try {
			return clienteRepository.save(cliente);
		} catch (DataIntegrityViolationException ex) {
			throw new CpfUniqueViolationException(String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", cliente.getCpf()));
		}
	}


	@Transactional(readOnly = true)
	public Cliente buscarPorId(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Cliente " + id + " Não encontrado no sistema"));
	}

	@Transactional(readOnly = true)
	public Page<ClienteProjection> buscarTodos(Pageable pageable) {
		return clienteRepository.findAllPageable(pageable);
	}
	
}
