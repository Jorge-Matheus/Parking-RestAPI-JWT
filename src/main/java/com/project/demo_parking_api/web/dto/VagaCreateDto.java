package com.project.demo_parking_api.web.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class VagaCreateDto {

	@NotBlank
	@Size(min = 4, max = 4)
	private String codigo;
 
	@NotBlank
	@Pattern(regexp = "LIVRE|OCUPADA")
	private String status;
	
	public VagaCreateDto() {}

	public VagaCreateDto(@NotBlank @Size(min = 4, max = 4) String codigo,
			@NotBlank @Pattern(regexp = "LIVRE|OCUPADA") String status) {
		super();
		this.codigo = codigo;
		this.status = status;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
