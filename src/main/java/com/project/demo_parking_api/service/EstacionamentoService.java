package com.project.demo_parking_api.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo_parking_api.entity.Cliente;
import com.project.demo_parking_api.entity.ClienteVaga;
import com.project.demo_parking_api.entity.Vaga;
import com.project.demo_parking_api.utils.EstacionamentoUtils;

@Service
public class EstacionamentoService {

	@Autowired
	private ClienteVagaService clienteVagaService;
	
	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private VagaService vagaService;
	
	public EstacionamentoService() {}
	
	@Transactional
	public ClienteVaga checkIn(ClienteVaga clienteVaga) {
		Cliente cliente = clienteService.buscarPorCpf(clienteVaga.getCliente().getCpf());
		clienteVaga.setCliente(cliente);
		Vaga vaga = vagaService.buscarPorVagaLivre();
		vaga.setStatus(Vaga.StatusVaga.OCUPADA);
		clienteVaga.setVaga(vaga);
		clienteVaga.setDataEntrada(LocalDateTime.now());
		clienteVaga.setRecibo(EstacionamentoUtils.gerarRecibo());
		return clienteVagaService.salvar(clienteVaga);
	}
}
