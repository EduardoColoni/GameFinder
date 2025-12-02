# 🎮 GameFinder - Consumo de API com Spring Boot

Este projeto foi desenvolvido como parte da avaliação da disciplina, com
foco no tema **"Consumo de APIs Externas com Spring WebClient"**.

A aplicação é um buscador de jogos que consome dados em tempo real da
**RAWG Video Games Database API**, exibe informações detalhadas
(incluindo descrição) e realiza tratamento de erros.

------------------------------------------------------------------------

## 🚀 Tecnologias Utilizadas

-   **Java 17**: Linguagem base.
-   **Spring Boot 3.2.0**: Framework principal.
-   **Spring WebFlux (WebClient)**: Cliente HTTP moderno e
    não-bloqueante (requisito do projeto).
-   **Spring Web MVC**: Para a arquitetura da aplicação web.
-   **Thymeleaf**: Template engine para renderização do Front-end
    (HTML).
-   **Lombok**: Para redução de boilerplate (Getters/Setters
    automáticos).
-   **Maven**: Gerenciamento de dependências.

------------------------------------------------------------------------

## 💡 Decisões de Arquitetura (Importante para Apresentação)

Aqui estão os pontos técnicos cruciais para explicar ao professor
durante a defesa do projeto:

### 1. Por que Spring WebClient?

Utilizamos o `WebClient` em vez do antigo `RestTemplate` para atender
aos requisitos modernos do Spring Framework e à solicitação específica
do projeto. Ele é mais flexível e robusto para integrações HTTP.

### 2. Estratégia de Busca (O Problema da Descrição)

A API da RAWG **não retorna a descrição do jogo** no endpoint de busca
simples. Para resolver isso, implementamos uma lógica de **duas etapas**
no `RawgService`: 1. **Busca por Nome:** O sistema busca o jogo digitado
e recupera o `ID` dele. 2. **Busca de Detalhes:** Com o `ID`, o sistema
faz uma segunda requisição automática para pegar os detalhes completos
(incluindo a descrição/sinopse).

### 3. Modo Síncrono (`.block()`)

Embora o WebClient seja reativo (assíncrono) por padrão, optamos por
utilizar o método `.block()` para forçar a operação a ser síncrona. \*
**Motivo:** Manter a arquitetura **MVC simples** e compatível com o
Thymeleaf, sem adicionar a complexidade de programação reativa completa
no Front-end, focando estritamente na integração da API.

### 4. Tratamento de Erros

Implementamos blocos `try-catch` específicos para capturar exceções do
WebClient (`WebClientResponseException`). Se a API cair ou o jogo não
existir, o usuário recebe uma mensagem amigável no HTML em vez de uma
tela de erro genérica do servidor.

------------------------------------------------------------------------

## 🛠️ Como rodar o projeto (Windows)

Siga estes passos para executar o projeto localmente para apresentação:

### Pré-requisitos

-   JDK 17 ou superior instalado.
-   IntelliJ IDEA (recomendado) ou Eclipse.
-   Maven.

### Passo 1: Configurar a Chave da API

O projeto precisa de uma chave de API para funcionar. 1. Abra o arquivo:
`src/main/resources/application.properties` 2. Localize a linha
`rawg.api.key`. 3. Insira a chave (Key) gerada no site
https://rawg.io/apidocs.

Exemplo:

``` bash
rawg.api.key=SUA_CHAVE_AQUI_123456
```

### Passo 2: Executar

Esta seção detalha como iniciar a aplicação GameFinder.

**Via IntelliJ (Método Recomendado):**

1.  Abra o arquivo `GameFinderApplication.java`.
2.  Clique no botão **Play (▶)** verde no topo da janela (geralmente ao
    lado do método `main`).

**Via Terminal (CMD/PowerShell):**

Na pasta raiz do projeto (onde está o `pom.xml`), execute o comando
Maven:

``` bash
mvn spring-boot:run
```
