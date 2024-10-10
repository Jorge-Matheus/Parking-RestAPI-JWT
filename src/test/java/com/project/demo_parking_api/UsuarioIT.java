package com.project.demo_parking_api;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.project.demo_parking_api.web.dto.UsuarioCreateDto;
import com.project.demo_parking_api.web.dto.UsuarioSenhaDto;
import com.project.demo_parking_api.web.dto.mapper.UsuarioResponseDto;
import com.project.demo_parking_api.web.exception.ErrorMessage;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/resources/sql/usuarios/usuarios-insert.sql"
, executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/resources/sql/usuarios/usuarios-delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class UsuarioIT {
	
	
	@Autowired
	WebTestClient testClient;
	
	
	@Test
	public void createUsuario_ComUsernameEPasswordValidos_RetornarUsuarioCriadoComStatus201() {
	    UsuarioResponseDto responseBody = testClient
	        .post().uri("/api/v1/usuarios")
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(new UsuarioCreateDto("tody@email.com", "123456"))
	        .exchange()
	        .expectStatus().isCreated()
	        .expectBody(UsuarioResponseDto.class)
	        .returnResult().getResponseBody();
	    
	    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("tody@email.com");
	    org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
	}
	
	
	@Test
	public void createUsuario_ComUsernameInvalido_RetornarErrorMessageStatus422() {
	    ErrorMessage responseBody = testClient
	        .post().uri("/api/v1/usuarios")
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(new UsuarioCreateDto("", "123456"))
	        .exchange()
	        .expectStatus().isEqualTo(422)
	        .expectBody(ErrorMessage.class)
	        .returnResult().getResponseBody();
	    
	    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	
	
	 responseBody = testClient
		        .post().uri("/api/v1/usuarios")
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(new UsuarioCreateDto("tody@", "123456"))
		        .exchange()
		        .expectStatus().isEqualTo(422)
		        .expectBody(ErrorMessage.class)
		        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		

		
		responseBody = testClient
		.post().uri("/api/v1/usuarios")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(new UsuarioCreateDto("", "123456"))
		.exchange()
		.expectStatus().isEqualTo(422)
		.expectBody(ErrorMessage.class)
		.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	
	
		responseBody = testClient
		        .post().uri("/api/v1/usuarios")
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(new UsuarioCreateDto("tody@email.", "123456"))
		        .exchange()
		        .expectStatus().isEqualTo(422)
		        .expectBody(ErrorMessage.class)
		        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		}

	
	@Test
	public void createUsuario_ComPasswordInvalido_RetornarErrorMessageStatus422() {
	    ErrorMessage responseBody = testClient
	        .post().uri("/api/v1/usuarios")
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(new UsuarioCreateDto("tody@email.com", ""))
	        .exchange()
	        .expectStatus().isEqualTo(422)
	        .expectBody(ErrorMessage.class)
	        .returnResult().getResponseBody();
	    
	    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
	    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	
	
	 responseBody = testClient
		        .post().uri("/api/v1/usuarios")
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(new UsuarioCreateDto("tody@email.com", "123"))
		        .exchange()
		        .expectStatus().isEqualTo(422)
		        .expectBody(ErrorMessage.class)
		        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		

		
		responseBody = testClient
		.post().uri("/api/v1/usuarios")
		.contentType(MediaType.APPLICATION_JSON)
		.bodyValue(new UsuarioCreateDto("tody@email.com", "123456789"))
		.exchange()
		.expectStatus().isEqualTo(422)
		.expectBody(ErrorMessage.class)
		.returnResult().getResponseBody();
		
		org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
	
	
		responseBody = testClient
		        .post().uri("/api/v1/usuarios")
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(new UsuarioCreateDto("tody@email.", "123456"))
		        .exchange()
		        .expectStatus().isEqualTo(422)
		        .expectBody(ErrorMessage.class)
		        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		}

	
		@Test
		public void createUsuario_ComUsernameEPasswordValidos_RetornarErrorMessageComStatus409() {
		    ErrorMessage responseBody = testClient
		        .post().uri("/api/v1/usuarios")
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(new UsuarioCreateDto("ana@email.com", "123456"))
		        .exchange()
		        .expectStatus().isEqualTo(409)
		        .expectBody(ErrorMessage.class)
		        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(409);
		}
	
	
		@Test
		public void buscarUsuario_ComIdExistente_RetornarUsuarioCriadoComStatus200() {
		    UsuarioResponseDto responseBody = testClient
		        .get().uri("/api/v1/usuarios/100")
		        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
		        .exchange()
		        .expectStatus().isOk()
		        .expectBody(UsuarioResponseDto.class)
		        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(100);
		    org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
		    org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("ADMIN");
		    
		    responseBody = testClient
			        .get().uri("/api/v1/usuarios/101")
			        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
			        .exchange()
			        .expectStatus().isOk()
			        .expectBody(UsuarioResponseDto.class)
			        .returnResult().getResponseBody();
			    
			    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
			    org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
			    org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
			    org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
			    
			    responseBody = testClient
				        .get().uri("/api/v1/usuarios/101")
				        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
				        .exchange()
				        .expectStatus().isOk()
				        .expectBody(UsuarioResponseDto.class)
				        .returnResult().getResponseBody();
				    
				    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
				    org.assertj.core.api.Assertions.assertThat(responseBody.getId()).isEqualTo(101);
				    org.assertj.core.api.Assertions.assertThat(responseBody.getUsername()).isEqualTo("ana@email.com");
				    org.assertj.core.api.Assertions.assertThat(responseBody.getRole()).isEqualTo("CLIENTE");
			    
		}
		
		
		@Test
		public void buscarUsuario_ComIdInexistente_RetornarErrorMessageComStatus404() {
		    ErrorMessage responseBody = testClient
		        .get().uri("/api/v1/usuarios/0")
		        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
		        .exchange()
		        .expectStatus().isNotFound()
		        .expectBody(ErrorMessage.class)
		        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(404);
		}
		
		@Test
		public void buscarUsuario_ComUsuarioCliente_RetornarErrorMessageComStatus403() {
		    ErrorMessage responseBody = testClient
		        .get().uri("/api/v1/usuarios/102")
		        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
		        .exchange()
		        .expectStatus().isForbidden()
		        .expectBody(ErrorMessage.class)
		        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
		}
		
		
		@Test
		public void editarSenha_ComDadosValidos_RetornarUsuarioCriadoComStatus204() {
		    testClient
		        .patch().uri("/api/v1/usuarios/100")
		        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
		        .contentType(MediaType.APPLICATION_JSON)
		        .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
		        .exchange()
		        .expectStatus().isNoContent();
		    
		    testClient
	        .patch().uri("/api/v1/usuarios/101")
	        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
	        .contentType(MediaType.APPLICATION_JSON)
	        .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
	        .exchange()
	        .expectStatus().isNoContent();
		    
		}
		
		
		@Test
		public void editarSenha_ComUsuariosDiferentes_RetornarErrorMessageComStatus403() {
		    ErrorMessage responseBody = testClient
			        .patch().uri("/api/v1/usuarios/0")
			        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
			        .contentType(MediaType.APPLICATION_JSON)
			        .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
			        .exchange()
			        .expectStatus().isForbidden()
			        .expectBody(ErrorMessage.class)
			        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
		    
		    responseBody = testClient
			        .patch().uri("/api/v1/usuarios/0")
			        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
			        .contentType(MediaType.APPLICATION_JSON)
			        .bodyValue(new UsuarioSenhaDto("123456", "123456", "123456"))
			        .exchange()
			        .expectStatus().isForbidden()
			        .expectBody(ErrorMessage.class)
			        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(403);
		}
		
		
		@Test
		public void editarSenha_ComCamposInvalidos_RetornarErrorMessageComStatus400() {
		    ErrorMessage responseBody = testClient
			        .patch().uri("/api/v1/usuarios/100")
			        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
			        .contentType(MediaType.APPLICATION_JSON)
			        .bodyValue(new UsuarioSenhaDto("123456", "123456", "000000"))
			        .exchange()
			        .expectStatus().isEqualTo(400)
			        .expectBody(ErrorMessage.class)
			        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
		    
		    
		    responseBody = testClient
			        .patch().uri("/api/v1/usuarios/100")
			        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
			        .contentType(MediaType.APPLICATION_JSON)
			        .bodyValue(new UsuarioSenhaDto("000000", "123456", "123456"))
			        .exchange()
			        .expectStatus().isEqualTo(400)
			        .expectBody(ErrorMessage.class)
			        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(400);
		}
		
		@Test
		public void editarSenha_ComCamposInvalidos_RetornarErrorMessageComStatus422() {
		    ErrorMessage responseBody = testClient
			        .patch().uri("/api/v1/usuarios/100")
			        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
			        .contentType(MediaType.APPLICATION_JSON)
			        .bodyValue(new UsuarioSenhaDto("", "", ""))
			        .exchange()
			        .expectStatus().isEqualTo(422)
			        .expectBody(ErrorMessage.class)
			        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		    
		    
		    responseBody = testClient
			        .patch().uri("/api/v1/usuarios/100")
			        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
			        .contentType(MediaType.APPLICATION_JSON)
			        .bodyValue(new UsuarioSenhaDto("12345", "12345", "12345"))
			        .exchange()
			        .expectStatus().isEqualTo(422)
			        .expectBody(ErrorMessage.class)
			        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		    
		    
		    responseBody = testClient
			        .patch().uri("/api/v1/usuarios/100")
			        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
			        .contentType(MediaType.APPLICATION_JSON)
			        .bodyValue(new UsuarioSenhaDto("12345678", "1234578", "12345678"))
			        .exchange()
			        .expectStatus().isEqualTo(422)
			        .expectBody(ErrorMessage.class)
			        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody.getStatus()).isEqualTo(422);
		}
		
		@Test
		public void buscarTodosOsUsuario_RetornarStatus200() {
			List<UsuarioResponseDto> responseBody = testClient
		        .get().uri("/api/v1/usuarios")
		        .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
		        .exchange()
		        .expectStatus().isOk()
		        .expectBodyList(UsuarioResponseDto.class)
		        .returnResult().getResponseBody();
		    
		    org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
		    org.assertj.core.api.Assertions.assertThat(responseBody).isEqualTo(3);
		}
}
