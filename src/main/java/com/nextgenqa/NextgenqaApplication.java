package com.nextgenqa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Classe principal para inicializar a aplicação Spring Boot.
 * Marca o ponto de entrada e configura automaticamente o contexto do Spring.
 */
@SpringBootApplication
public class NextgenqaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextgenqaApplication.class, args);
        System.out.println("Aplicação NextgenQA iniciada com sucesso!");
    }
}
