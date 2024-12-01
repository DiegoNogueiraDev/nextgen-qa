package com.nextgenqa.utils;

import org.yaml.snakeyaml.Yaml;
import java.io.InputStream;
import java.util.Map;

public class YamlReader {

    public static Map<String, Object> readYaml(String filePath) {
        Yaml yaml = new Yaml();
        try (InputStream inputStream = YamlReader.class.getClassLoader().getResourceAsStream(filePath)) {
            return yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao ler o arquivo YAML: " + e.getMessage(), e);
        }
    }
}
