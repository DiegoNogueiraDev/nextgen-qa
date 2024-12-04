package com.nextgenqa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class IAService {

    private static final Logger logger = LoggerFactory.getLogger(IAService.class);

    public String generateXPathSuggestion(String xPath, String dom) {
        // Simula interação com IA
        logger.info("Enviando prompt para IA...");
        String prompt = String.format(
                "Estou tentando localizar o elemento com XPath: '%s'. Aqui está o DOM atual: %s. Por favor, sugira um novo XPath.",
                xPath, dom);

        // Chamar o modelo local ou simular resposta
        String response = mockResponse(prompt);
        logger.info("IA retornou: {}", response);

        return response;
    }

    private String mockResponse(String prompt) {
        // Simula a resposta de uma IA regenerativa
        return "//div[@id='suggested_xpath']";
    }
}
