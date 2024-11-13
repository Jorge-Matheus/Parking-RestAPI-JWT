package com.project.demo_parking_api.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.demo_parking_api.entity.ClienteVaga;
import com.project.demo_parking_api.service.ClienteVagaService;
import com.project.demo_parking_api.service.EstacionamentoService;
import com.project.demo_parking_api.web.dto.ClienteResponseDto;
import com.project.demo_parking_api.web.dto.EstacionamentoCreateDto;
import com.project.demo_parking_api.web.dto.EstacionamentoResponseDto;
import com.project.demo_parking_api.web.dto.mapper.ClienteVagaMapper;
import com.project.demo_parking_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Estaciomentos", description = "Operação de registro de entrada e saida de um veiculo do estacionamento")

@RestController
@RequestMapping("api/v1/estacionamentos")
public class EstacionamentoController {

	@Autowired
	private EstacionamentoService estacionamentoService;

	@Autowired
	private ClienteVagaService clienteVagaService;
	
	@Operation(summary = "Operação de check-in", description = "Recurso para dar entrada de um veiculo no estaciomaneto "
			+ "Requisição exige uso de um bearer token. Acesso restrito a Role='CLIENTE'", security = @SecurityRequirement(name = "security"), responses = {
					@ApiResponse(responseCode = "201", description = "Recurso criado com sucesso",headers = @Header(name = HttpHeaders.LOCATION, description = "URL de acesso de recurso criado") ,content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = EstacionamentoResponseDto.class))),
					@ApiResponse(responseCode = "404", description = "Causas possiveis, CPF do cliente não cadastrado no sistema, CPF do clliente não cadastrado  o sistema, Nenhuma vaga livre foi localizada",content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "422", description = "Recurso não processado por falta de dados ou dados inválidos" ,content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
					@ApiResponse(responseCode = "403", description = "Recurso não permitido ao perfil CLIENTE" ,content = @Content(mediaType = " application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),

			})
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
	
	
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
	@GetMapping("/check-in/{recibo}")
	public ResponseEntity<EstacionamentoResponseDto> geyByRecibo(@PathVariable String recibo) {
		ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
		EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);
		return ResponseEntity.ok(dto);
	}
	
}
