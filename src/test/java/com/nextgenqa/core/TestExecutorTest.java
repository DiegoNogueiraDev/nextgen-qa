package com.nextgenqa.core;

import com.nextgenqa.adapters.SeleniumAdapter;
import com.nextgenqa.domain.TestScenario;
import com.nextgenqa.domain.TestScenarioWrapper;
import com.nextgenqa.utils.YamlParser;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestExecutorTest {

    private SeleniumAdapter seleniumAdapter = new SeleniumAdapter();

    @ParameterizedTest
    @ValueSource(strings = {
            "src/main/resources/tests/linkedin_login_test.yaml",
            "src/main/resources/tests/google_search_test.yaml"
    })
    void testScenarioExecution(String yamlPath) {
        TestExecutor executor = new TestExecutor(seleniumAdapter);

        assertDoesNotThrow(() -> {
            TestScenarioWrapper wrapper = YamlParser.parse(yamlPath);
            TestScenario scenario = wrapper.getTest_scenario();
            executor.execute(scenario);
        });

        // Verificar se o resultado esperado ocorreu (exemplo: URL ou elemento na página)
        String currentUrl = seleniumAdapter.getCurrentUrl();
        assertNotNull(currentUrl, "A URL atual não deve ser nula!");
        System.out.println("Teste concluído. URL final: " + currentUrl);
    }

    @AfterEach
    void tearDown() {
        seleniumAdapter.closeBrowser();
    }
}
