package com.project.demo_parking_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo_parking_api.entity.ClienteVaga;
import com.project.demo_parking_api.repositories.ClienteVagaRepository;

@Service
public class ClienteVagaService {

	
	@Autowired
	private ClienteVagaRepository repository;
	
	
	public ClienteVagaService() {}
	
	@Transactional
	public ClienteVaga salvar(ClienteVaga clienteVaga) {
		return repository.save(clienteVaga);
	}
}
