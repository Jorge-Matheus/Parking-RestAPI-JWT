package com.project.demo_parking_api.web.dto.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import com.project.demo_parking_api.web.controller.dto.PageableDto;

public class PageableMapper {

	public PageableMapper() {}
	
	
	public static PageableDto toDto(Page page) {
		return new ModelMapper().map(page, PageableDto.class);
	}
}
