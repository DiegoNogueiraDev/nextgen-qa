package com.nextgenqa.domain;

import lombok.Data;

@Data
public class TestStep {
    private String action;  // Ação a ser executada
    private String campo;   // Nome ou seletor do campo (ex.: "username")
    private String valor;   // Valor a ser preenchido
    private String url;     // URL para navegação
    private String botao;   // Nome ou seletor do botão
    private String texto;   // Texto esperado para validação
}
