package com.project.demo_parking_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.project.demo_parking_api.jwt.JwtToken;
import com.project.demo_parking_api.web.controller.dto.UsuarioLoginDto;
import com.project.demo_parking_api.web.exception.ErrorMessage;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/resources/sql/usuarios/usuarios-insert.sql"
, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/resources/sql/usuarios/usuarios-delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class AutenticacaoIT {

	@Autowired
	WebTestClient testClient;
	
	@Test
	public void autenticar_ComCredenciaisValidas_RetornarTokenComStatus200() {
		JwtToken responseBody = testClient
			.post()
			.uri("/api/v1/auth")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(new UsuarioLoginDto("ana@email.com", "123456"))
			.exchange()
			.expectStatus().isOk()
			.expectBody(JwtToken.class)
			.returnResult().getResponseBody();
			
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	}
	
	
	@Test
	public void autenticar_ComCredenciaisInvalidas_RetornarErrorMessageStatus400() {
		ErrorMessage responseBody = testClient
			.post()
			.uri("/api/v1/auth")
			.contentType(MediaType.APPLICATION_JSON)
			.bodyValue(new UsuarioLoginDto("invalido@email.com", "123456"))
			.exchange()
			.expectStatus().isBadRequest()
			.expectBody(ErrorMessage.class)
			.returnResult().getResponseBody();
			
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
		
		
		responseBody = testClient
				.post()
				.uri("/api/v1/auth")
				.contentType(MediaType.APPLICATION_JSON)
				.bodyValue(new UsuarioLoginDto("ana@email.com", "000000"))
				.exchange()
				.expectStatus().isBadRequest()
				.expectBody(ErrorMessage.class)
				.returnResult().getResponseBody();
				
			org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
	}
}
