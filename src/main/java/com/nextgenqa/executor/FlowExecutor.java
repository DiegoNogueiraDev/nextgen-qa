package com.nextgenqa.executor;

import com.nextgenqa.model.Flow;
import com.nextgenqa.model.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;

public class FlowExecutor {

    private static final Logger logger = LoggerFactory.getLogger(FlowExecutor.class);
    private final WebDriver driver;

    public FlowExecutor(WebDriver driver) {
        this.driver = driver;
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

    private boolean executeStepWithFallback(Step step) {
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

    private List<String> getFallbacks(Step step) {
        // Busca os fallbacks no banco ou arquivo YAML
        // Simulação para fins de exemplo:
        return List.of(
                step.getXPath().replace("[@id=", "[@name="),  // Exemplo: substitui ID por NAME
                step.getXPath().replace("input", "textarea") // Exemplo: altera de INPUT para TEXTAREA
        );
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
