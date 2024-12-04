package com.nextgenqa;

import com.nextgenqa.executor.FlowExecutor;
import com.nextgenqa.model.Flow;
import com.nextgenqa.service.YamlLoaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.List;

@SpringBootApplication
public class NextgenqaApplication {

    @Autowired
    private static ResourceLoader resourceLoader;

    public static void main(String[] args) throws IOException {
        SpringApplication.run(NextgenqaApplication.class, args);

        // Carregar os fluxos
        YamlLoaderService yamlLoaderService = new YamlLoaderService();
        List<Flow> flows = yamlLoaderService.loadFlows("flows.yaml");

        // Criar o executor
        FlowExecutor executor = new FlowExecutor();

        // Abrir página local ou pública
        executor.openUrl(resourceLoader.getResource("classpath:test.html").getURL().toString());

        // Executar o fluxo de formulário
        for (Flow flow : flows) {
            if (flow.getName().equals("contact_form")) {
                executor.executeFlow(flow);
            }
        }
    }
}
