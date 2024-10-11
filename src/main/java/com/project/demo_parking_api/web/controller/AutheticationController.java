package com.project.demo_parking_api.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo_parking_api.jwt.JwtToken;
import com.project.demo_parking_api.jwt.JwtUserDetailsService;
import com.project.demo_parking_api.web.controller.dto.UsuarioLoginDto;
import com.project.demo_parking_api.web.dto.mapper.UsuarioResponseDto;
import com.project.demo_parking_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "Autenticação", description = "Recurso para proceder com a autenticação na API")
@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AutheticationController {
	
	private final JwtUserDetailsService detailsService;

	private final AuthenticationManager authenticationManager;
	
	public AutheticationController(JwtUserDetailsService detailsService, AuthenticationManager authenticationManager) {
		super();
		this.detailsService = detailsService;
		this.authenticationManager = authenticationManager;
	}

	
	@Operation(
			summary = "Autenticar na API", description = "Recurso de autenticação na API",
			responses = {
					@ApiResponse(responseCode = "200"
							, description = "Autenticação realizada com sucesso e retorno de um bearer token"
							, content = @Content(mediaType = "application/json", 
							schema = @Schema(implementation = UsuarioResponseDto.class)))
						,@ApiResponse(responseCode = "400", description = "Credenciais invalidas",
						content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))),
						@ApiResponse(responseCode = "422", description = "Campo(s) Inválidos(s)",
						content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorMessage.class))
								)
			})
	@PostMapping("/auth")
	public ResponseEntity<?> autenticar(@RequestBody @Valid UsuarioLoginDto dto, HttpServletRequest request) {
		System.out.println("Processo de autenticação pelo login " + dto.getUsername());
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
			authenticationManager.authenticate(authenticationToken);
			JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
			System.out.println("Token JWT gerado: " + token.getToken());
			return ResponseEntity.ok(token);
			
		} catch(AuthenticationException ex) {
			System.out.println("Bad Credentials from username " + dto.getUsername());
		}
		return ResponseEntity
				.badRequest()
				.body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credencials Inválidas"));
	}
}
