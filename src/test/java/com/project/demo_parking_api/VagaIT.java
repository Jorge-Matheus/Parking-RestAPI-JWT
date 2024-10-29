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
import com.project.demo_parking_api.web.exception.ErrorMessage;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/resources/sql/vagas/vagas-insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/resources/sql/vagas/vagas-delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class VagaIT {

	@Autowired
	WebTestClient testClient;

	@Test
	public void criarVaga_ComDadosValidos_RetornarLocationStatus201() {
		testClient.post().uri("/api/v1/vagas").contentType(MediaType.APPLICATION_JSON)
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
				.bodyValue(new VagaCreateDto("A-06", "LIVRE")).exchange().expectStatus().isCreated().expectHeader()
				.exists(HttpHeaders.LOCATION);
	}

	@Test
	public void criarVaga_ComCodigoJaExistente_RetornarErrorMessage409() {
		testClient.post().uri("/api/v1/vagas").contentType(MediaType.APPLICATION_JSON)
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
				.bodyValue(new VagaCreateDto("A-02", "LIVRE")).exchange().expectStatus().isEqualTo(409).expectBody()
				.jsonPath("status").isEqualTo(409).jsonPath("method").isEqualTo("POST").jsonPath("path")
				.isEqualTo("/api/v1/vagas");
	}

	@Test
	public void criarVaga_ComDadosInvalidos_RetornarErrorMessage422() {
		testClient.post().uri("/api/v1/vagas").contentType(MediaType.APPLICATION_JSON)
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
				.bodyValue(new VagaCreateDto("", "")).exchange().expectStatus().isEqualTo(422).expectBody()
				.jsonPath("status").isEqualTo(22).jsonPath("method").isEqualTo("POST").jsonPath("path")
				.isEqualTo("/api/v1/vagas");

		testClient.post().uri("/api/v1/vagas").contentType(MediaType.APPLICATION_JSON)
				.headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
				.bodyValue(new VagaCreateDto("A-501", "DESOCUPADA")).exchange().expectStatus().isEqualTo(422).expectBody()
				.jsonPath("status").isEqualTo(22).jsonPath("method").isEqualTo("POST").jsonPath("path")
				.isEqualTo("/api/v1/vagas");
	}

}
