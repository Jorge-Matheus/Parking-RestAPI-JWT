package com.project.demo_parking_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.project.demo_parking_api.web.dto.ClienteCreateDto;
import com.project.demo_parking_api.web.dto.ClienteResponseDto;
import com.project.demo_parking_api.web.exception.ErrorMessage;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/resources/sql/clientes/clientes-insert.sql")
@Sql(scripts = "/resources/sql/clientes/clientes-delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class ClienteIT {

	@Autowired
	WebTestClient testClient;
	
	
	@Test
	public void criarCliente_ComDadosValidos_RetornarClienteComStatus201() {
		ClienteResponseDto responseBody = testClient.post()
		.uri("/api/v1/clientes")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
		.bodyValue(new ClienteCreateDto("Tobias Ferreira", "18524929022"))
		.exchange()
		.expectStatus().isCreated()
		.expectBody(ClienteResponseDto.class)
		.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getNome()).isEqualTo("Tobias Ferreira");
	    org.assertj.core.api.Assertions.assertThat(responseBody.getCpf()).isEqualTo("18524929022");
	}
	
	
	@Test
	public void criarCliente_ComCpfJaCadastrado_RetornarErrorMessageStatus409() {
		ErrorMessage responseBody = testClient.post()
		.uri("/api/v1/clientes")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
		.bodyValue(new ClienteCreateDto("Tobias Ferreira", "99839149059"))
		.exchange()
		.expectStatus().isEqualTo(409)
		.expectBody(ErrorMessage.class)
		.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
	}
	
	
	@Test
	public void criarCliente_ComDadosInvalidos_RetornarErrorMessageStatus422() {
		ErrorMessage responseBody = testClient.post()
		.uri("/api/v1/clientes")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
		.bodyValue(new ClienteCreateDto("", ""))
		.exchange()
		.expectStatus().isEqualTo(422)
		.expectBody(ErrorMessage.class)
		.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	    
	    responseBody = testClient.post()
	    		.uri("/api/v1/clientes")
	    		.contentType(MediaType.APPLICATION_JSON)
	    		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
	    		.bodyValue(new ClienteCreateDto("Bobb", "00000000000"))
	    		.exchange()
	    		.expectStatus().isEqualTo(422)
	    		.expectBody(ErrorMessage.class)
	    		.returnResult().getResponseBody();
	    		
	    		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    	    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	    	    
	    	    
	    	    responseBody = testClient.post()
	    	    		.uri("/api/v1/clientes")
	    	    		.contentType(MediaType.APPLICATION_JSON)
	    	    		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "toby@email.com", "123456"))
	    	    		.bodyValue(new ClienteCreateDto("Bobb", "998.391.490-59"))
	    	    		.exchange()
	    	    		.expectStatus().isEqualTo(422)
	    	    		.expectBody(ErrorMessage.class)
	    	    		.returnResult().getResponseBody();
	    	    		
	    	    		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    	    	    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	}
	
	@Test
	public void criarCliente_ComUsuarioNaoPermitido_RetornarErrorMessageStatus403() {
		ErrorMessage responseBody = testClient.post()
		.uri("/api/v1/clientes")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
		.bodyValue(new ClienteCreateDto("Tobias Ferreira", "99839149059"))
		.exchange()
		.expectStatus().isEqualTo(403)
		.expectBody(ErrorMessage.class)
		.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
	}
	
	
}
