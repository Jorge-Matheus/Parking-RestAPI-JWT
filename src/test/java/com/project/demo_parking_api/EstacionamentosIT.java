package com.project.demo_parking_api;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.project.demo_parking_api.web.dto.EstacionamentoCreateDto;

@Configuration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/resources/sql/estacionamentos/estacionamentos-insert.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/resources/sql/estacionamentos/estacionamentos-delete.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
public class EstacionamentosIT {

	@Autowired
	WebTestClient testClient;

	@Test
	public void criarCheckin_ComDadosValidos_RetornarCreatedAndLocation() {
		EstacionamentoCreateDto createDto = new EstacionamentoCreateDto();
		createDto.setPlaca("WER-1111");
		createDto.setMarca("FIAT");
		createDto.setModelo("PALIO 1.0");
		createDto.setCor("AZUL");
		createDto.setClienteCpf("65434217039");
		
		testClient.post().uri("/api/v1/estacionamentos/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
		.bodyValue(createDto)
		.exchange()
		.expectStatus().isCreated()
		.expectHeader().exists(HttpHeaders.LOCATION)
		.expectBody()
		.jsonPath("placa").isEqualTo("WER-1111")
		.jsonPath("marca").isEqualTo("FIAT")
		.jsonPath("modelo").isEqualTo("PALIO 1.0")
		.jsonPath("cor").isEqualTo("AZUL")
		.jsonPath("clienteCpf").isEqualTo("65434217039")
		.jsonPath("recibo").exists()
		.jsonPath("dataEntrada").exists()
		.jsonPath("vagaCodigo");
	}
	
	@Test
	public void criarCheckIn_ComRoleCliente_RetornarErrorStatus403() {
		testClient.post().uri("/api/v1/estacionamentos/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
		.exchange()
		.expectStatus().isForbidden()
		.expectBody()
		.jsonPath("status").isEqualTo("403")
		.jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		.jsonPath("method").isEqualTo("POST");
	}
	
	@Test
	public void criarCheckIn_ComDadosInvalidos_RetornarErrorStatus422() {
		EstacionamentoCreateDto createDto = new EstacionamentoCreateDto();
		createDto.setPlaca("");
		createDto.setMarca("");
		createDto.setModelo("");
		createDto.setCor("");
		createDto.setClienteCpf("");
		
		testClient.post().uri("/api/v1/estacionamentos/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com", "123456"))
		.exchange()
		.expectStatus().isEqualTo(422)
		.expectBody()
		.jsonPath("status").isEqualTo("422")
		.jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		.jsonPath("method").isEqualTo("POST");
	}
	
	
	@Test
	public void criarCheckIn_ComCpfInexistente_RetornarErrorStatus404() {
		EstacionamentoCreateDto createDto = new EstacionamentoCreateDto();
		createDto.setPlaca("WER-1111");
		createDto.setMarca("FIAT");
		createDto.setModelo("PALIO 1.0");
		createDto.setCor("AZUL");
		createDto.setClienteCpf("32226967052");
		
		testClient.post().uri("/api/v1/estacionamentos/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
		.exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.jsonPath("status").isEqualTo("404")
		.jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		.jsonPath("method").isEqualTo("POST");
	}
	
	
	@Sql(scripts = "/resources/sql/estacionamentos/estacionamentos-insert-vagas-ocupadas.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
	@Sql(scripts = "/resources/sql/estacionamentos/estacionamentos-delete-vagas-ocupadas.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
	@Test
	public void criarCheckIn_ComVagasOcupadas_RetornarErrorStatus404() {
		EstacionamentoCreateDto createDto = new EstacionamentoCreateDto();
		createDto.setPlaca("WER-1111");
		createDto.setMarca("FIAT");
		createDto.setModelo("PALIO 1.0");
		createDto.setCor("AZUL");
		createDto.setClienteCpf("65434217039");
		
		testClient.post().uri("/api/v1/estacionamentos/check-in")
		.contentType(MediaType.APPLICATION_JSON)
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
		.exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.jsonPath("status").isEqualTo("404")
		.jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		.jsonPath("method").isEqualTo("POST");
	}
}
