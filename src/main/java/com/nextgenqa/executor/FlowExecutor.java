package com.nextgenqa.executor;

import com.nextgenqa.model.Flow;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class FlowExecutor {

    private WebDriver driver;

    public FlowExecutor() {
        // Configuração do WebDriver (ajustar o caminho do driver)
        System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver");
        this.driver = new ChromeDriver();
    }

    /**
     * Executa um fluxo.
     *
     * @param flow O fluxo a ser executado.
     */
    public void executeFlow(Flow flow) {
        System.out.println("Executando fluxo: " + flow.getName());

        for (Flow.Step step : flow.getSteps()) {
            executeStep(step);
        }

        // Fecha o navegador após a execução
        driver.quit();
    }

    /**
     * Executa um passo individual do fluxo.
     *
     * @param step O passo a ser executado.
     */
    private void executeStep(Flow.Step step) {
        try {
            switch (step.getAction().toLowerCase()) {
                case "click":
                    WebElement elementToClick = driver.findElement(By.xpath(step.getXpath()));
                    elementToClick.click();
                    break;
                case "input":
                    WebElement inputField = driver.findElement(By.xpath(step.getXpath()));
                    inputField.sendKeys(step.getValue());
                    break;
                default:
                    System.out.println("Ação desconhecida: " + step.getAction());
            }
        } catch (Exception e) {
            System.err.println("Erro ao executar o passo: " + step.getAction());
            e.printStackTrace();
        }
    }

    /**
     * Abre uma URL no navegador.
     *
     * @param url A URL a ser aberta.
     */
    public void openUrl(String url) {
        driver.get(url);
    }
}
