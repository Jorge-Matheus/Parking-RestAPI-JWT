package com.project.demo_parking_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo_parking_api.entity.ClienteVaga;
import com.project.demo_parking_api.exception.EntityNotFoundException;
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

	@Transactional(readOnly = true)
	public ClienteVaga buscarPorRecibo(String recibo) {
		return repository.findByReciboAndDataSaidaIsNull(recibo).orElseThrow(() -> new EntityNotFoundException("Recibo " + recibo + " não encontrado no sistema ou check-out já realizado."));
	}
	
	@Transactional(readOnly = true)
	public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
		return repository.countByClienteCpfAndDataSaidaIsNotNull(cpf);
	}
}
