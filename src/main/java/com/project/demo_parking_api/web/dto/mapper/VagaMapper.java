package com.project.demo_parking_api.web.dto.mapper;

import org.modelmapper.ModelMapper;

import com.project.demo_parking_api.entity.Vaga;
import com.project.demo_parking_api.web.dto.VagaCreateDto;
import com.project.demo_parking_api.web.dto.VagaResponseDto;

public class VagaMapper {

	private VagaMapper() {}
	
	public static Vaga toVaga(VagaCreateDto dto) {
		return new ModelMapper().map(dto, Vaga.class);
	}
	
	public static VagaResponseDto toDto(Vaga vaga) {
		return new ModelMapper().map(vaga, VagaResponseDto.class);
	}
	
}
