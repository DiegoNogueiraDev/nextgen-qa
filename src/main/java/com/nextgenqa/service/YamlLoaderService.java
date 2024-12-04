package com.nextgenqa.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nextgenqa.model.Flow;

import java.io.InputStream;
import java.util.List;

public class YamlLoaderService {

    /**
     * Carrega os fluxos de um arquivo YAML.
     *
     * @param fileName Nome do arquivo YAML.
     * @return Lista de fluxos carregados.
     */
    public List<Flow> loadFlows(String fileName) {
        ObjectMapper mapper = new ObjectMapper(new com.fasterxml.jackson.dataformat.yaml.YAMLFactory());
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            return mapper.readValue(inputStream, new TypeReference<>() {});
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo YAML: " + fileName, e);
        }
    }
}
