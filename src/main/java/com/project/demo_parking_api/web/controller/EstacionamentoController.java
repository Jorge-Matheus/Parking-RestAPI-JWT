package com.project.demo_parking_api.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.demo_parking_api.entity.ClienteVaga;
import com.project.demo_parking_api.service.EstacionamentoService;
import com.project.demo_parking_api.web.dto.EstacionamentoCreateDto;
import com.project.demo_parking_api.web.dto.EstacionamentoResponseDto;
import com.project.demo_parking_api.web.dto.mapper.ClienteVagaMapper;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

	@Autowired
	private EstacionamentoService estacionamentoService;
	
	
	@PostMapping("/check-in")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<EstacionamentoResponseDto> checkIn(@RequestBody @Valid EstacionamentoCreateDto dto) {
		ClienteVaga clienteVaga = ClienteVagaMapper.toClienteVaga(dto);
		estacionamentoService.checkIn(clienteVaga);
		EstacionamentoResponseDto responseDto = ClienteVagaMapper.toDto(clienteVaga);
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequestUri().path("/{recibo}")
				.buildAndExpand(clienteVaga.getRecibo())
				.toUri();
		return ResponseEntity.created(location).body(responseDto);
	}
}
