package com.senacsp.projetosemestral.bibliotecapessoal;

import org.springframework.boot.SpringApplication;

public class TestBibliotecapessoalApplication {

	public static void main(String[] args) {
		SpringApplication.from(BibliotecapessoalApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
