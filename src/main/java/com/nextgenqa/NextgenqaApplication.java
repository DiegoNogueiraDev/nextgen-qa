package com.nextgenqa;

import com.nextgenqa.executor.FlowExecutor;
import com.nextgenqa.model.Flow;
import com.nextgenqa.service.FallbackService;
import com.nextgenqa.service.IAService;
import com.nextgenqa.service.PythonApiService;
import com.nextgenqa.service.YamlLoaderService;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class NextgenqaApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(NextgenqaApplication.class);

    private final ResourceLoader resourceLoader;

    @Autowired
    public NextgenqaApplication(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public static void main(String[] args) {
        SpringApplication.run(NextgenqaApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Aplicação NextgenQA iniciada. Carregando fluxos do YAML...");

        // Inicializar o Python API Service
        PythonApiService pythonApiService = new PythonApiService("src/main/resources/python/api.py");
        pythonApiService.startPythonServer();

        // Carregar fluxos do YAML
        YamlLoaderService yamlLoaderService = new YamlLoaderService();
        List<Flow> flows;
        try {
            flows = yamlLoaderService.loadFlows("flows.yaml");
            logger.info("Fluxos carregados com sucesso. Total de fluxos: {}", flows.size());
        } catch (Exception e) {
            logger.error("Erro ao carregar fluxos do arquivo YAML.", e);
            pythonApiService.stopPythonServer();
            return;
        }

        // Configurar WebDriver
        WebDriver driver = null;
        try {
            driver = new ChromeDriver(); // Certifique-se de configurar o driver adequadamente
            FlowExecutor executor = new FlowExecutor(driver, new FallbackService(), new IAService());

            // Obter o caminho do HTML de teste
            String htmlPath = resourceLoader.getResource("classpath:test.html").getURL().toString();
            logger.info("Abrindo arquivo HTML: {}", htmlPath);

            executor.openUrl(htmlPath);

            // Executar os fluxos
            for (Flow flow : flows) {
                try {
                    executor.executeFlow(flow);
                    logger.info("Fluxo '{}' executado com sucesso.", flow.getName());
                } catch (Exception e) {
                    logger.error("Erro ao executar o fluxo '{}'.", flow.getName(), e);
                }
            }
        } catch (Exception e) {
            logger.error("Erro ao inicializar o WebDriver ou executar os fluxos.", e);
        } finally {
            if (driver != null) {
                driver.quit();
                logger.info("WebDriver finalizado.");
            }
            pythonApiService.stopPythonServer();
        }
    }
}
