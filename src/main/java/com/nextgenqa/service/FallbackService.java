package com.nextgenqa.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@SuppressWarnings("unchecked")
public class FallbackService {

    private final String fallbackFile = "src/main/resources/fallbacks.yaml";
    private Map<String, Object> fallbackData;
    private final ObjectMapper yamlMapper;
    private static final Logger logger = LoggerFactory.getLogger(FallbackService.class);

    public FallbackService() throws IOException {
        yamlMapper = new ObjectMapper(new YAMLFactory());
        loadFallbacks();
    }

    public void loadFallbacks() throws IOException {
        File file = new File(fallbackFile);
        if (!file.exists()) {
            logger.warn("Arquivo de fallbacks.yaml não encontrado. Criando um novo arquivo vazio.");
            fallbackData = Map.of("fallbacks", List.of());
            saveFallbacks(); // Cria o arquivo vazio
        } else {
            fallbackData = yamlMapper.readValue(file, new TypeReference<>() {});
            logger.info("Fallbacks carregados com sucesso do arquivo.");
        }
    }

    public List<String> getFallbacks(String action, String xPath) {
        logger.info("Obtendo fallbacks para ação: {}, XPath: {}", action, xPath);
        return ((List<Map<String, Object>>) fallbackData.get("fallbacks"))
                .stream()
                .filter(fb -> fb.get("action").equals(action) && fb.get("xPath").equals(xPath))
                .findFirst()
                .map(fb -> (List<String>) fb.get("alternatives"))
                .orElseGet(() -> {
                    logger.warn("Nenhum fallback encontrado para ação: {}, XPath: {}", action, xPath);
                    return List.of();
                });
    }

    public void addFallback(String action, String xPath, String newFallback) {
        try {
            logger.info("Adicionando novo fallback: [Action: {}, XPath: {}, Alternative: {}]", action, xPath, newFallback);
            List<Map<String, Object>> fallbacks = (List<Map<String, Object>>) fallbackData.get("fallbacks");

            // Encontrar o fallback correspondente ou criar um novo
            Map<String, Object> targetFallback = fallbacks.stream()
                    .filter(fb -> fb.get("action").equals(action) && fb.get("xPath").equals(xPath))
                    .findFirst()
                    .orElseGet(() -> {
                        Map<String, Object> newFallbackEntry = Map.of(
                                "action", action,
                                "xPath", xPath,
                                "alternatives", List.of()
                        );
                        fallbacks.add(newFallbackEntry);
                        return newFallbackEntry;
                    });

            List<String> alternatives = (List<String>) targetFallback.get("alternatives");
            if (!alternatives.contains(newFallback)) {
                alternatives.add(newFallback);
                logger.info("Novo fallback adicionado com sucesso.");
            } else {
                logger.info("Fallback já existente: {}", newFallback);
            }

            saveFallbacks();
        } catch (Exception e) {
            logger.error("Erro ao adicionar fallback: [Action: {}, XPath: {}, Alternative: {}]. Detalhes: {}", action, xPath, newFallback, e.getMessage());
        }
    }

    public void removeFallback(String action, String xPath, String fallback) {
        try {
            logger.info("Removendo fallback: [Action: {}, XPath: {}, Alternative: {}]", action, xPath, fallback);
            List<Map<String, Object>> fallbacks = (List<Map<String, Object>>) fallbackData.get("fallbacks");

            fallbacks.stream()
                    .filter(fb -> fb.get("action").equals(action) && fb.get("xPath").equals(xPath))
                    .findFirst()
                    .ifPresent(fb -> {
                        List<String> alternatives = (List<String>) fb.get("alternatives");
                        if (alternatives.remove(fallback)) {
                            logger.info("Fallback removido com sucesso.");
                        } else {
                            logger.warn("Fallback não encontrado na lista de alternativas.");
                        }
                    });

            saveFallbacks();
        } catch (Exception e) {
            logger.error("Erro ao remover fallback: [Action: {}, XPath: {}, Alternative: {}]. Detalhes: {}", action, xPath, fallback, e.getMessage());
        }
    }

    private void saveFallbacks() throws IOException {
        yamlMapper.writeValue(new File(fallbackFile), fallbackData);
        logger.info("Fallbacks salvos com sucesso no arquivo.");
    }
}
