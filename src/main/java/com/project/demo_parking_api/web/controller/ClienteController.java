package com.project.demo_parking_api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo_parking_api.entity.Cliente;
import com.project.demo_parking_api.jwt.JwtUserDetails;
import com.project.demo_parking_api.service.ClienteService;
import com.project.demo_parking_api.service.UsuarioService;
import com.project.demo_parking_api.web.dto.ClienteCreateDto;
import com.project.demo_parking_api.web.dto.ClienteMapper;
import com.project.demo_parking_api.web.dto.ClienteResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/v1/clientes")
public class ClienteController {

	@Autowired
	private ClienteService clienteService;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@PostMapping
	@PreAuthorize("hasRole('CLIENTE')")
	public ResponseEntity<ClienteResponseDto> create(@RequestBody @Valid ClienteCreateDto dto, 
			@AuthenticationPrincipal JwtUserDetails userDetails) {
		Cliente cliente = ClienteMapper.toCliente(dto);	
		cliente.setUsuario(usuarioService.buscarPorId(userDetails.getId()));
		clienteService.salvar(cliente);
		return ResponseEntity.status(201).body(ClienteMapper.toDto(cliente));
	}
}
