package com.nextgenqa.domain;

import lombok.Data;

@Data
public class TestScenarioWrapper {
    private TestScenario test_scenario; // Este campo mapeia o campo raiz no YAML
}
