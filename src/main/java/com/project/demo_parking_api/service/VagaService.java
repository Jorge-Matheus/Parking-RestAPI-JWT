package com.project.demo_parking_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.project.demo_parking_api.entity.Vaga;
import com.project.demo_parking_api.exception.CodigoUniqueViolationException;
import com.project.demo_parking_api.exception.EntityNotFoundException;
import com.project.demo_parking_api.repositories.VagaRepository;

@Service
public class VagaService {

	@Autowired
	private VagaRepository vagaRepository;
	
	@Transactional
	public Vaga salvar(Vaga vaga) {
		try {
			return vagaRepository.save(vaga);
		} catch (DataIntegrityViolationException ex) {
			throw new CodigoUniqueViolationException("Vaga com código " + vaga.getCodigo() + " já cadastrada.");
		}
	}
	
	
	@Transactional(readOnly = true)
	public Vaga buscarPorCodigo(String codigo) {
		return vagaRepository.findByCodigo(codigo).orElseThrow(() -> new EntityNotFoundException("Vaga código " + codigo + " não foi encontrada"));
	}
	
	
	public VagaService() {}

	
	@Transactional(readOnly = true)
	public Vaga buscarPorVagaLivre() {
		return vagaRepository.findFirstByStatus(Vaga.StatusVaga.LIVRE)
				.orElseThrow(() -> new EntityNotFoundException("Nenhuma vaga livre foi encontrada!"));
	}
}
