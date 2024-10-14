package com.project.demo_parking_api.web.dto;

import org.modelmapper.ModelMapper;

import com.project.demo_parking_api.entity.Cliente;

public class ClienteMapper {

	
	public static Cliente toCliente(ClienteCreateDto dto) {
		return new ModelMapper().map(dto, Cliente.class);
	}
	
	public static ClienteResponseDto toDto(Cliente cliente) {
		return new ModelMapper().map(cliente, ClienteResponseDto.class);
	}
	
	private ClienteMapper() {}
	
	
	
}
