package com.project.demo_parking_api.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.demo_parking_api.jwt.JwtToken;
import com.project.demo_parking_api.jwt.JwtUserDetailsService;
import com.project.demo_parking_api.web.controller.dto.UsuarioLoginDto;
import com.project.demo_parking_api.web.exception.ErrorMessage;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class AutheticationController {

	@Autowired
	private JwtUserDetailsService detailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/auth")
	public ResponseEntity<?> autenticar(@RequestBody UsuarioLoginDto dto, HttpServletRequest request) {
		System.out.println("Processo de autenticação pelo login " + dto.getUsername());
		try {
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
			authenticationManager.authenticate(authenticationToken);
			JwtToken token = detailsService.getTokenAuthenticated(dto.getUsername());
			return ResponseEntity.ok(token);
			
		} catch(AuthenticationException ex) {
			System.out.println("Bad Credentials from username " + dto.getUsername());
		}
		return ResponseEntity
				.badRequest()
				.body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Credencials Inválidas"));
	}
}
