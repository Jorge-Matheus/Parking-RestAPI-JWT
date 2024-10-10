package com.project.demo_parking_api;

import java.util.function.Consumer;

import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.project.demo_parking_api.jwt.JwtToken;
import com.project.demo_parking_api.web.controller.dto.UsuarioLoginDto;

public class JwtAuthentication {

	
	public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password) {
		String token = client.post().uri("/api/v1/auth")
				.bodyValue(new UsuarioLoginDto(username, password))
				.exchange()
				.expectStatus().isOk()
				.expectBody(JwtToken.class)
				.returnResult().getResponseBody().getToken();
		return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
	}
}
