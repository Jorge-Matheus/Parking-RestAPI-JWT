package com.project.demo_parking_api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.project.demo_parking_api.web.dto.VagaCreateDto;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/resources/sql/vagas/vagas-insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/resources/sql/vagas/vagas-delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class VagaIT {

	  @Autowired
	    WebTestClient testClient;

	    @Test
	    public void criarVaga_ComDadosValidos_RetornarLocationStatus201() {
	        testClient
	                .post()
	                .uri("/api/v1/vagas")
	                .contentType(MediaType.APPLICATION_JSON)
	                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
	                .bodyValue(new VagaCreateDto("A-07", "LIVRE"))
	                .exchange()
	                .expectStatus().isCreated()
	                .expectHeader().exists(HttpHeaders.LOCATION);
	    }

	    @Test
	    public void criarVaga_ComCodigoJaExistente_RetornarErrorMessageComStatus409() {
	        testClient
	                .post()
	                .uri("/api/v1/vagas")
	                .contentType(MediaType.APPLICATION_JSON)
	                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
	                .bodyValue(new VagaCreateDto("A-02", "LIVRE"))
	                .exchange()
	                .expectStatus().isEqualTo(409)
	                .expectBody()
	                .jsonPath("status").isEqualTo(409)
	                .jsonPath("method").isEqualTo("POST")
	                .jsonPath("path").isEqualTo("/api/v1/vagas");

	    }

	    @Test
	    public void criarVaga_ComDadoInvalidos_RetornarErrorMessageComStatus422() {
	        testClient
	                .post()
	                .uri("/api/v1/vagas")
	                .contentType(MediaType.APPLICATION_JSON)
	                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
	                .bodyValue(new VagaCreateDto("", ""))
	                .exchange()
	                .expectStatus().isEqualTo(422)
	                .expectBody()
	                .jsonPath("status").isEqualTo(422)
	                .jsonPath("method").isEqualTo("POST")
	                .jsonPath("path").isEqualTo("/api/v1/vagas");

	        testClient
	                .post()
	                .uri("/api/v1/vagas")
	                .contentType(MediaType.APPLICATION_JSON)
	                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
	                .bodyValue(new VagaCreateDto("A-501", "DESOCUPADA"))
	                .exchange()
	                .expectStatus().isEqualTo(422)
	                .expectBody()
	                .jsonPath("status").isEqualTo(422)
	                .jsonPath("method").isEqualTo("POST")
	                .jsonPath("path").isEqualTo("/api/v1/vagas");
	    }


	    @Test
	    public void buscarVaga_ComCodigoExistente_RetornarVagaComStatus200() {
	        testClient
	                .get()
	                .uri("/api/v1/vagas/{codigo}", "A-02")
	                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
	                .exchange()
	                .expectStatus().isOk()
	                .expectBody()
	                .jsonPath("id").isEqualTo(10)
	                .jsonPath("codigo").isEqualTo("A-02")
	                .jsonPath("status").isEqualTo("LIVRE");

	    }

	    @Test
	    public void buscarVaga_ComCodigoInexistente_RetornarErrorMessageComStatus404() {
	        testClient
	                .get()
	                .uri("/api/v1/vagas/{codigo}", "A-10")
	                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
	                .exchange()
	                .expectStatus().isNotFound()
	                .expectBody()
	                .jsonPath("status").isEqualTo(404)
	                .jsonPath("method").isEqualTo("GET")
	                .jsonPath("path").isEqualTo("/api/v1/vagas/A-10");
	    }

	    @Test
	    public void buscarVaga_ComUsuarioSemPermissaoDeAcesso_RetornarErrorMessageComStatus403() {
	        testClient
	                .get()
	                .uri("/api/v1/vagas/{codigo}", "A-01")
	                 .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
	                .exchange()
	                .expectStatus().isForbidden()
	                .expectBody()
	                .jsonPath("status").isEqualTo(403)
	                .jsonPath("method").isEqualTo("GET")
	                .jsonPath("path").isEqualTo("/api/v1/vagas/A-01");
	    }

	    @Test
	    public void criarVaga_ComUsuarioSemPermissaoDeAcesso_RetornarErrorMessageComStatus403() {
	        testClient
	                .post()
	                .uri("/api/v1/vagas")
	                .contentType(MediaType.APPLICATION_JSON)
	                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
	                .bodyValue(new VagaCreateDto("A-05", "OCUPADA"))
	                .exchange()
	                .expectStatus().isForbidden()
	                .expectBody()
	                .jsonPath("status").isEqualTo(403)
	                .jsonPath("method").isEqualTo("POST")
	                .jsonPath("path").isEqualTo("/api/v1/vagas");
	    }
}
