package com.VidaPlus.VitaValore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VitaValoreApplication {

	public static void main(String[] args) {

		SpringApplication.run(VitaValoreApplication.class, args);
		System.out.println("Servidor esta Rodando em http://localhost:3000");
	}

}
