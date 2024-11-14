package com.project.demo_parking_api.service;

import java.math.BigDecimal;
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

	@Transactional
	public ClienteVaga checkOut(String recibo) {
		ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
		LocalDateTime dataSaida = LocalDateTime.now();
		BigDecimal valor = EstacionamentoUtils.calcularCusto(clienteVaga.getDataEntrada(), dataSaida);
		clienteVaga.setValor(valor);
		long totalDeVezes = clienteVagaService
				.getTotalDeVezesEstacionamentoCompleto(clienteVaga.getCliente().getCpf());
		BigDecimal desconto = EstacionamentoUtils.calcularDesconto(valor, totalDeVezes);
		clienteVaga.setDesconto(desconto);
		clienteVaga.setDataSaida(dataSaida);
		clienteVaga.getVaga().setStatus(Vaga.StatusVaga.LIVRE);
		return clienteVagaService.salvar(clienteVaga);
	}
}
