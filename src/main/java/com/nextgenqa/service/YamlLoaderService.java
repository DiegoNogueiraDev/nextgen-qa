package com.nextgenqa.service;

import com.nextgenqa.model.Flow;
import org.yaml.snakeyaml.Yaml;

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
        Yaml yaml = new Yaml();
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            return yaml.loadAs(inputStream, List.class);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo YAML: " + fileName, e);
        }
    }
}
