package com.project.demo_parking_api.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.demo_parking_api.entity.Vaga;
import com.project.demo_parking_api.service.VagaService;
import com.project.demo_parking_api.web.dto.VagaCreateDto;
import com.project.demo_parking_api.web.dto.VagaResponseDto;
import com.project.demo_parking_api.web.dto.mapper.VagaMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/vagas")
public class VagaController {

	@Autowired
	private VagaService vagaService;
	
	@PostMapping
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<Void> create(@RequestBody @Valid VagaCreateDto dto) {
		Vaga vaga = VagaMapper.toVaga(dto);
		vagaService.salvar(vaga);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequestUri().path("/{codigo}")
				.buildAndExpand(vaga.getCodigo())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/{codigo}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<VagaResponseDto> getByCodigo(@PathVariable String codigo) {
		Vaga vaga = vagaService.buscarPorCodigo(codigo);
		return ResponseEntity.ok(VagaMapper.toDto(vaga));
	}
}
