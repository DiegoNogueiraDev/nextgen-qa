package com.nextgenqa.api;

import com.nextgenqa.core.TestExecutor;
import com.nextgenqa.domain.TestScenario;
import com.nextgenqa.utils.YamlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tests")
public class TestController {

    private final TestExecutor testExecutor;

    @Autowired
    public TestController(TestExecutor testExecutor) {
        this.testExecutor = testExecutor;
    }

    @PostMapping("/execute")
    public String executeTest(@RequestParam String yamlPath) {
        try {
            TestScenario scenario = YamlParser.parse(yamlPath).getTest_scenario();
            testExecutor.execute(scenario);
            return "Teste executado com sucesso!";
        } catch (Exception e) {
            return "Erro durante a execução: " + e.getMessage();
        }
    }
}
