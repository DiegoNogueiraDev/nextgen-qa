package com.nextgenqa.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class PythonApiService {

    private static final Logger logger = LoggerFactory.getLogger(PythonApiService.class);
    private final String pythonScriptPath;
    private Process pythonProcess;

    public PythonApiService(String pythonScriptPath) {
        this.pythonScriptPath = pythonScriptPath;
    }

    public void startPythonServer() {
        try {
            logger.info("Iniciando o servidor Python...");
            pythonProcess = new ProcessBuilder("python3", pythonScriptPath).start();

            // Aguarde um curto período para o servidor iniciar
            Thread.sleep(3000);

            // Verifique se o servidor está respondendo
            if (isServerRunning()) {
                logger.info("Servidor Python iniciado com sucesso.");
            } else {
                logger.error("Falha ao verificar a disponibilidade do servidor Python.");
            }
        } catch (Exception e) {
            logger.error("Erro ao iniciar o servidor Python.", e);
        }
    }

    public void stopPythonServer() {
        if (pythonProcess != null) {
            pythonProcess.destroy();
            logger.info("Servidor Python finalizado.");
        }
    }

    private boolean isServerRunning() {
        try {
            URL url = new URL("http://localhost:8000/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            int responseCode = connection.getResponseCode();
            return responseCode == 200;
        } catch (IOException e) {
            logger.error("Erro ao verificar a disponibilidade do servidor Python.", e);
            return false;
        }
    }
}
