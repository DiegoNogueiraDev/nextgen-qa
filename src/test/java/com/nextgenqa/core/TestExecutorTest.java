package com.nextgenqa.core;

import com.nextgenqa.adapters.SeleniumAdapter;
import com.nextgenqa.domain.TestScenario;
import com.nextgenqa.domain.TestScenarioWrapper;
import com.nextgenqa.utils.YamlParser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TestExecutorTest {

    @Test
    void testLinkedInLoginScenario() {
        SeleniumAdapter seleniumAdapter = new SeleniumAdapter();
        TestExecutor executor = new TestExecutor(seleniumAdapter);

        assertDoesNotThrow(() -> {
            TestScenarioWrapper wrapper = YamlParser.parse("src/main/resources/tests/linkedin_login_test.yaml");
            TestScenario scenario = wrapper.getTest_scenario();
            executor.execute(scenario);
        });

        seleniumAdapter.closeBrowser();
    }

}
