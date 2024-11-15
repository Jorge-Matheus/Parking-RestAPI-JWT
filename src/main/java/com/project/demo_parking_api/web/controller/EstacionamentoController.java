package com.project.demo_parking_api.web.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.demo_parking_api.entity.ClienteVaga;
import com.project.demo_parking_api.jwt.JwtUserDetails;
import com.project.demo_parking_api.repositories.projection.ClienteVagaProjection;
import com.project.demo_parking_api.service.ClienteVagaService;
import com.project.demo_parking_api.service.EstacionamentoService;
import com.project.demo_parking_api.web.controller.dto.PageableDto;
import com.project.demo_parking_api.web.dto.EstacionamentoCreateDto;
import com.project.demo_parking_api.web.dto.EstacionamentoResponseDto;
import com.project.demo_parking_api.web.dto.mapper.ClienteVagaMapper;
import com.project.demo_parking_api.web.dto.mapper.PageableMapper;
import com.project.demo_parking_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
	
	
	@Operation(summary = "Localizar um veículo estacionado", description = "Recurso para retornar um veículo estacionado " +
            "pelo nº do recibo. Requisição exige uso de um bearer token.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                @Parameter(in = ParameterIn.PATH, name = "recibo", description = "Número do rebibo gerado pelo check-in")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Número do recibo não encontrado.",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
	@PreAuthorize("hasAnyRole('ADMIN', 'CLIENTE')")
	@GetMapping("/check-in/{recibo}")
	public ResponseEntity<EstacionamentoResponseDto> geyByRecibo(@PathVariable String recibo) {
		ClienteVaga clienteVaga = clienteVagaService.buscarPorRecibo(recibo);
		EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);
		return ResponseEntity.ok(dto);
	}
	
	
	@Operation(summary = "Operação de check-out", description = "Recurso para dar saída de um veículo do estacionamento. " +
            "Requisição exige uso de um bearer token. Acesso restrito a Role='ADMIN'",
            security = @SecurityRequirement(name = "security"),
            parameters = { @Parameter(in = ParameterIn.PATH, name = "recibo", description = "Número do rebibo gerado pelo check-in",
                    required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso atualzado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                    @ApiResponse(responseCode = "404", description = "Número do recibo inexistente ou " +
                            "o veículo já passou pelo check-out.",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/check-out/{recibo}")
	public ResponseEntity<EstacionamentoResponseDto> checkOut(@PathVariable String recibo) {
		ClienteVaga clienteVaga = estacionamentoService.checkOut(recibo);
		EstacionamentoResponseDto dto = ClienteVagaMapper.toDto(clienteVaga);
		return ResponseEntity.ok(dto);
	}
	
	@Operation(summary = "Localizar os registros de estacionamentos do cliente por CPF", description = "Localizar os " +
            "registros de estacionamentos do cliente por CPF. Requisição exige uso de um bearer token.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.PATH, name = "cpf", description = "Nº do CPF referente ao cliente a ser consultado",
                            required = true
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "page", description = "Representa a página retornada",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0"))
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "size", description = "Representa o total de elementos por página",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5"))
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", description = "Campo padrão de ordenação 'dataEntrada,asc'. ",
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "dataEntrada,asc")),
                            hidden = true
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = PageableDto.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de CLIENTE",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
	@GetMapping("/cpf/{cpf}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PageableDto> getAllEstacionamentosPorCpf(@PathVariable String cpf, @Parameter(hidden = true)
    @PageableDefault(size = 5, sort = "dataEntrada",
            direction = Sort.Direction.ASC) Pageable pageable) {
        Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorClienteCpf(cpf, pageable);
        PageableDto dto = PageableMapper.toDto(projection);
        return ResponseEntity.ok(dto);
    }
	
	
	@Operation(summary = "Localizar os registros de estacionamentos do cliente logado",
            description = "Localizar os registros de estacionamentos do cliente logado. " +
                    "Requisição exige uso de um bearer token.",
            security = @SecurityRequirement(name = "security"),
            parameters = {
                    @Parameter(in = ParameterIn.QUERY, name = "page",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "0")),
                            description = "Representa a página retornada"
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "size",
                            content = @Content(schema = @Schema(type = "integer", defaultValue = "5")),
                            description = "Representa o total de elementos por página"
                    ),
                    @Parameter(in = ParameterIn.QUERY, name = "sort", hidden = true,
                            array = @ArraySchema(schema = @Schema(type = "string", defaultValue = "dataEntrada,asc")),
                            description = "Campo padrão de ordenação 'dataEntrada,asc'. ")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Recurso localizado com sucesso",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = EstacionamentoResponseDto.class))),
                    @ApiResponse(responseCode = "403", description = "Recurso não permito ao perfil de ADMIN",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
	@GetMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<PageableDto> getAllEstacionamentosDoCliente(@AuthenticationPrincipal JwtUserDetails user,
                                                                      @Parameter(hidden = true) @PageableDefault(
                                                                              size = 5, sort = "dataEntrada",
                                                                              direction = Sort.Direction.ASC) Pageable pageable) {

        Page<ClienteVagaProjection> projection = clienteVagaService.buscarTodosPorUsuarioId(user.getId(), pageable);
        PageableDto dto = PageableMapper.toDto(projection);
        return ResponseEntity.ok(dto);
    }
	
}
