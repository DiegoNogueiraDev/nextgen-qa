package com.nextgenqa.utils;

import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import com.nextgenqa.domain.TestScenarioWrapper;

import java.io.File;

public class YamlParser {

    public static TestScenarioWrapper parse(String yamlPath) throws Exception {
        YAMLMapper mapper = new YAMLMapper();
        return mapper.readValue(new File(yamlPath), TestScenarioWrapper.class);
    }
}
