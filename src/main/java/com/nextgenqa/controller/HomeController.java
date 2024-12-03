package com.nextgenqa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para lidar com requisições à rota raiz.
 */
@RestController
public class HomeController {

    /**
     * Mapeia a rota raiz ("/") para retornar uma mensagem simples.
     *
     * @return Mensagem de boas-vindas.
     */
    @GetMapping("/")
    public String home() {
        return "Bem-vindo à aplicação NextgenQA!";
    }
}
