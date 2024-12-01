# NextGenQA

Framework de Testes Flexíveis para Automação, com YAML e Integração de Fallbacks e Preditivos.

## Funcionalidades
- Testes flexíveis baseados em YAML.
- Execução com fallback e análise de erros.
- Suporte ao Selenium para automação web.
- Pronto para integração com CI/CD.

## Requisitos
- Java 17
- Maven
- Navegador Chrome
- Chromedriver compatível com sua versão do Chrome

## Como Usar
1. Clone este repositório:
   ```bash
   git clone https://github.com/DiegoNogueiraDev/nextgen-qa.git
    ```
2. Instale as dependências:
   ```bash
   mvn clean install
   ```
   
3. Execute os testes:
   ```bash
   mvn test
   ```
## Contribuindo

Sinta-se à vontade para abrir "issues" ou enviar "pull requests" com melhorias ou correções.

---

### **4. Subir o Workflow no GitHub Actions**
1. Após criar o arquivo de configuração (`build-and-test.yml`), faça o commit e o push:
   ```bash
   git add .github/workflows/build-and-test.yml
   git commit -m "Adicionando pipeline de build e testes"
   git push origin master
