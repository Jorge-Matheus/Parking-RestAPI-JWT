package com.project.demo_parking_api.utils;

import java.time.LocalDateTime;

public class EstacionamentoUtils {

	// 2023-03-16T15:23:48.616463500
	// 20230316-152121
	
	public static String gerarRecibo() {
		LocalDateTime date = LocalDateTime.now();
		String recibo = date.toString().substring(0,19);
		return recibo.replace("-", "").replace(":", "").replace("T", "-");
	}
	
	
	private EstacionamentoUtils() {}
	
}
