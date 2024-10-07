package com.VidaPlus.VitaValore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VitaValoreApplication {

	public static void main(String[] args) {

		SpringApplication.run(VitaValoreApplication.class, args);
		String port = System.getenv("PORT");

		System.out.println("Servidor está rodando em http://localhost:" + port);

	}

}
