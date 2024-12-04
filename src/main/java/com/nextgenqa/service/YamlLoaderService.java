package com.nextgenqa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.nextgenqa.model.Flow;
import org.springframework.stereotype.Service;
import java.io.InputStream;
import java.util.List;

@Service
public class YamlLoaderService {
    public List<Flow> loadFlows(String fileName) {
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName)) {
            if (inputStream == null) {
                throw new RuntimeException("Arquivo YAML não encontrado: " + fileName);
            }

            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(inputStream, mapper.getTypeFactory().constructCollectionType(List.class, Flow.class));
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar o arquivo YAML: " + fileName, e);
        }
    }
}
