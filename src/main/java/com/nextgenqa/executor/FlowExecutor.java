package com.nextgenqa.executor;

import com.nextgenqa.model.Flow;
import com.nextgenqa.model.Step;
import com.nextgenqa.service.FallbackService;
import com.nextgenqa.service.IAService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class FlowExecutor {

    private static final Logger logger = LoggerFactory.getLogger(FlowExecutor.class);
    private final WebDriver driver;
    private final FallbackService fallbackService;
    private final IAService iaService;

    public FlowExecutor(WebDriver driver, FallbackService fallbackService, IAService iaService) {
        this.driver = driver;
        this.fallbackService = fallbackService;
        this.iaService = iaService;
    }

    private List<String> getFallbacks(Step step) {
        return fallbackService.getFallbacks(step.getAction(), step.getXPath());
    }

    public void openUrl(String url) {
        try {
            driver.get(url);
            logger.info("Navegador direcionado para a URL: {}", url);
        } catch (Exception e) {
            logger.error("Erro ao abrir a URL: {}", url, e);
        }
    }

    public void executeFlow(Flow flow) {
        logger.info("Iniciando execução do fluxo: {}", flow.getName());

        for (Step step : flow.getSteps()) {
            boolean success = executeStepWithFallback(step);

            if (!success) {
                logger.error("Nenhum fallback funcionou para o passo: {}. Enviando informações para IA.", step);
                String iaSuggestion = getIAFallbackSuggestion(step);
                logger.info("IA sugeriu: {}", iaSuggestion != null ? iaSuggestion : "Nenhuma solução encontrada.");
            }
        }

        logger.info("Fluxo '{}' concluído.", flow.getName());
    }

    private boolean executeStepWithFallback(Step step) throws IOException {
        // Tenta executar o passo principal
        if (executeStep(step)) return true;

        logger.warn("Falha ao executar passo principal: {}", step);
        List<String> fallbacks = getFallbacks(step);

        // Tenta os fallbacks
        for (String fallbackXpath : fallbacks) {
            logger.info("Tentando fallback: {}", fallbackXpath);
            Step fallbackStep = new Step(step.getAction(), fallbackXpath, step.getValue());
            if (executeStep(fallbackStep)) return true;
        }

        // Interação com a IA
        String dom = driver.getPageSource();
        String iaSuggestion = iaService.generateXPathSuggestion(step.getXPath(), dom);

        if (iaSuggestion != null && !iaSuggestion.isEmpty()) {
            logger.info("Tentando sugestão da IA: {}", iaSuggestion);
            Step iaStep = new Step(step.getAction(), iaSuggestion, step.getValue());
            if (executeStep(iaStep)) {
                // Atualiza o serviço de fallbacks com a nova sugestão
                fallbackService.addFallback(step.getAction(), step.getXPath(), iaSuggestion);
                return true;
            }
        }

        return false;
    }

    private boolean executeStep(Step step) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(step.getXPath())));

            switch (step.getAction().toLowerCase()) {
                case "click":
                    element.click();
                    break;
                case "input":
                    if (step.getValue() != null) {
                        element.clear();
                        element.sendKeys(step.getValue());
                    } else {
                        throw new IllegalArgumentException("O valor para o campo de input é nulo.");
                    }
                    break;
                default:
                    throw new UnsupportedOperationException("Ação desconhecida: " + step.getAction());
            }
            return true;
        } catch (Exception e) {
            logger.error("Erro ao executar o passo: [Ação: {}, XPath: {}, Valor: {}]. Detalhes: {}",
                    step.getAction(), step.getXPath(), step.getValue(), e.getMessage());
            return false;
        }
    }

    private String getIAFallbackSuggestion(Step step) {
        try {
            String dom = driver.getPageSource(); // Obtém o DOM atual
            String prompt = String.format(
                    "Estou tentando localizar o elemento com XPath: '%s'. Aqui está o DOM atual: %s. Você pode sugerir um novo XPath?",
                    step.getXPath(), dom);

            // Simulação de integração com IA:
            return mockIaResponse(prompt);
        } catch (Exception e) {
            logger.error("Erro ao interagir com a IA para o passo: {}", step, e);
            return null;
        }
    }

    private String mockIaResponse(String prompt) {
        logger.info("Enviando o seguinte prompt para a IA: {}", prompt);
        // Retorna uma resposta simulada:
        return "//div[@id='suggested_xpath']";
    }
}
