package com.nextgenqa.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
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

    public FallbackService() throws IOException {
        loadFallbacks();
    }

    public void loadFallbacks() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File file = new File(fallbackFile);
        fallbackData = mapper.readValue(file, Map.class);
    }

    public List<String> getFallbacks(String action, String xPath) {
        return ((List<Map<String, Object>>) fallbackData.get("fallbacks"))
                .stream()
                .filter(fb -> fb.get("action").equals(action) && fb.get("xPath").equals(xPath))
                .findFirst()
                .map(fb -> (List<String>) fb.get("alternatives"))
                .orElse(null);
    }

    public void addFallback(String action, String xPath, String newFallback) throws IOException {
        ((List<Map<String, Object>>) fallbackData.get("fallbacks"))
                .stream()
                .filter(fb -> fb.get("action").equals(action) && fb.get("xPath").equals(xPath))
                .findFirst()
                .ifPresent(fb -> ((List<String>) fb.get("alternatives")).add(newFallback));

        saveFallbacks();
    }

    private void saveFallbacks() throws IOException {
        ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        File file = new File(fallbackFile);
        mapper.writeValue(file, fallbackData);
    }
}
