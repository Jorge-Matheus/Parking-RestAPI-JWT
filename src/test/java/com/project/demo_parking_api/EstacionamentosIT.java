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

import com.project.demo_parking_api.web.controller.dto.PageableDto;
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
		.bodyValue(createDto)
		.exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.jsonPath("status").isEqualTo("404")
		.jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in")
		.jsonPath("method").isEqualTo("POST");
	}
	
	@Test
	public void buscarCheckIn_ComPerfilAdmin_RetornarDadosStatus200() {
		testClient.get().uri("/api/v1/estacionamentos/check-in/{recibo}", "20230313-101300")
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com", "123456"))
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("placa").isEqualTo("FIT-1020")
		.jsonPath("marca").isEqualTo("FIAT")
		.jsonPath("modelo").isEqualTo("PALIO")
		.jsonPath("cor").isEqualTo("VERDE")
		.jsonPath("clienteCpf").isEqualTo("99839149059")
		.jsonPath("recibo").isEqualTo("20230313-101300")
		.jsonPath("dataEntrada").isEqualTo("2023-03-13 10:15:00")
		.jsonPath("vagaCodigo").isEqualTo("A-03");
	}
	
	
	@Test
	public void buscarCheckIn_ComPerfilCliente_RetornarDadosStatus200() {
		testClient.get().uri("/api/v1/estacionamentos/check-in/{recibo}", "20230313-101300")
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@email.com", "123456"))
		.exchange()
		.expectStatus().isOk()
		.expectBody()
		.jsonPath("placa").isEqualTo("FIT-1020")
		.jsonPath("marca").isEqualTo("FIAT")
		.jsonPath("modelo").isEqualTo("PALIO")
		.jsonPath("cor").isEqualTo("VERDE")
		.jsonPath("clienteCpf").isEqualTo("99839149059")
		.jsonPath("recibo").isEqualTo("20230313-101300")
		.jsonPath("dataEntrada").isEqualTo("2023-03-13 10:15:00")
		.jsonPath("vagaCodigo").isEqualTo("A-03");
	}
	
	@Test
	public void buscarCheckIn_ComPerfilInexistente_RetornarErrorStatus404() {
		testClient.get().uri("/api/v1/estacionamentos/check-in/{recibo}", "23456313-901300")
		.headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@email.com", "123456"))
		.exchange()
		.expectStatus().isNotFound()
		.expectBody()
		.jsonPath("status").isEqualTo("404")
		.jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-in/23456313-901300")
		.jsonPath("method").isEqualTo("GET	");
	}
	
	
	@Test
    public void criarCheckOut_ComReciboExistente_RetornarSucesso() {

        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("placa").isEqualTo("FIT-1020")
                .jsonPath("marca").isEqualTo("FIAT")
                .jsonPath("modelo").isEqualTo("PALIO")
                .jsonPath("cor").isEqualTo("VERDE")
                .jsonPath("dataEntrada").isEqualTo("2023-03-13 10:15:00")
                .jsonPath("clienteCpf").isEqualTo("98401203015")
                .jsonPath("vagaCodigo").isEqualTo("A-01")
                .jsonPath("recibo").isEqualTo("20230313-101300")
                .jsonPath("dataSaida").exists()
                .jsonPath("valor").exists()
                .jsonPath("desconto").exists();
    }
	
	@Test
    public void criarCheckOut_ComReciboInexistente_RetornarErrorStatus404() {

        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-000000")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .exchange()
                .expectStatus().isNotFound()
                .expectBody()
                .jsonPath("status").isEqualTo("404")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20230313-000000")
                .jsonPath("method").isEqualTo("PUT");
    }

    @Test
    public void criarCheckOut_ComRoleCliente_RetornarErrorStatus403() {

        testClient.put()
                .uri("/api/v1/estacionamentos/check-out/{recibo}", "20230313-101300")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com.br", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/check-out/20230313-101300")
                .jsonPath("method").isEqualTo("PUT");
    }
    
    @Test
    public void buscarEstacionamentos_PorClienteCpf_RetornarSucesso() {

        PageableDto responseBody = testClient.get()
                .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=0", "98401203015")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

        responseBody = testClient.get()
                .uri("/api/v1/estacionamentos/cpf/{cpf}?size=1&page=1", "98401203015")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);
    }
    
    @Test
    public void buscarEstacionamentos_PorClienteCpfComPerfilCliente_RetornarErrorStatus403() {

        testClient.get()
                .uri("/api/v1/estacionamentos/cpf/{cpf}", "98401203015")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bia@email.com.br", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos/cpf/98401203015")
                .jsonPath("method").isEqualTo("GET");
    }
    
    @Test
    public void buscarEstacionamentos_DoClienteLogado_RetornarSucesso() {

        PageableDto responseBody = testClient.get()
                .uri("/api/v1/estacionamentos?size=1&page=0")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@email.com.br", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);

        responseBody = testClient.get()
                .uri("/api/v1/estacionamentos?size=1&page=1")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "bob@email.com.br", "123456"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(PageableDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
        org.assertj.core.api.Assertions.assertThat(responseBody.getContent().size()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getTotalPages()).isEqualTo(2);
        org.assertj.core.api.Assertions.assertThat(responseBody.getNumber()).isEqualTo(1);
        org.assertj.core.api.Assertions.assertThat(responseBody.getSize()).isEqualTo(1);
    }
    
    @Test
    public void buscarEstacionamentos_DoClienteLogadoPerfilAdmin_RetornarErrorStatus403() {

        testClient.get()
                .uri("/api/v1/estacionamentos")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana@email.com.br", "123456"))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody()
                .jsonPath("status").isEqualTo("403")
                .jsonPath("path").isEqualTo("/api/v1/estacionamentos")
                .jsonPath("method").isEqualTo("GET");
    }

}
