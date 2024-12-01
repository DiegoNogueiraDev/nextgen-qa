package com.nextgenqa.core;

import com.nextgenqa.adapters.SeleniumAdapter;
import com.nextgenqa.domain.TestScenario;
import com.nextgenqa.domain.TestStep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestExecutor {

    private final SeleniumAdapter seleniumAdapter;

    @Autowired
    public TestExecutor(SeleniumAdapter seleniumAdapter) {
        this.seleniumAdapter = seleniumAdapter;
    }

    public void execute(TestScenario scenario) {
        System.out.println("Executando cenário: " + scenario.getName());
        for (TestStep step : scenario.getSteps()) {
            try {
                executeStep(step);
            } catch (Exception e) {
                System.err.println("Erro na etapa: " + step.getAction() + ". Tentando fallback...");
                executeFallback(scenario.getFallback());
                break;
            }
        }
    }

    private void executeStep(TestStep step) throws Exception {
        switch (step.getAction()) {
            case "abrir_página":
                seleniumAdapter.openPage(step.getUrl());
                break;
            case "preencher_campo":
                seleniumAdapter.fillField(step.getCampo(), step.getValor());
                break;
            case "clicar_botao":
                seleniumAdapter.clickButton(step.getBotao());
                break;
            default:
                throw new Exception("Ação desconhecida: " + step.getAction());
        }
    }

    private void executeFallback(List<TestStep> fallbackSteps) {
        if (fallbackSteps == null || fallbackSteps.isEmpty()) {
            System.out.println("Nenhum fallback configurado.");
            return;
        }
        for (TestStep step : fallbackSteps) {
            try {
                executeStep(step);
            } catch (Exception e) {
                System.err.println("Erro no fallback: " + step.getAction());
            }
        }
    }
}
