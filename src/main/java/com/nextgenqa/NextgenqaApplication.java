package com.nextgenqa;

import com.nextgenqa.model.Flow;
import com.nextgenqa.service.YamlLoaderService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class NextgenqaApplication {

    public static void main(String[] args) {
        SpringApplication.run(NextgenqaApplication.class, args);

        // Teste do carregamento do YAML
        YamlLoaderService yamlLoaderService = new YamlLoaderService();
        List<Flow> flows = yamlLoaderService.loadFlows("flows.yaml");

        // Imprime os fluxos carregados
        for (Flow flow : flows) {
            System.out.println("Fluxo: " + flow.getName());
            for (Flow.Step step : flow.getSteps()) {
                System.out.println(" - Ação: " + step.getAction());
                System.out.println("   XPath: " + step.getXpath());
                if (step.getValue() != null) {
                    System.out.println("   Valor: " + step.getValue());
                }
            }
        }
    }
}
