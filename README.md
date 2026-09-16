# 🏢 Portal de Recursos Humanos API

API RESTful desenvolvida em **Java com Spring Boot** para simular um sistema de gerenciamento de Recursos Humanos, permitindo controlar funcionários, registros de ponto, horas trabalhadas, horas extras e relatórios mensais de jornada.

O projeto foi desenvolvido com foco em **boas práticas de desenvolvimento backend**, separação de responsabilidades, validação de dados, tratamento centralizado de exceções, autenticação e autorização utilizando **OAuth 2.0 / OpenID Connect**, documentação com **Swagger/OpenAPI** e persistência de dados utilizando **PostgreSQL**.

---

## 📌 Sobre o projeto

O **Portal de Recursos Humanos API** representa o backend de um sistema de controle de funcionários e jornadas de trabalho.

A aplicação permite:

      * Cadastrar funcionários;
      * Consultar funcionários;
      * Atualizar dados de funcionários;
      * Alterar senhas;
      * Remover funcionários;
      * Registrar horários de entrada e saída;
      * Consultar registros de ponto;
      * Remover registros de ponto;
      * Calcular horas trabalhadas;
      * Calcular horas extras;
      * Gerar relatórios mensais;
      * Controlar permissões através de roles;
      * Autenticar usuários utilizando OAuth 2.0;
      * Proteger a API através de JWT;
      * Documentar e testar os endpoints através do Swagger UI.

O projeto também implementa regras específicas relacionadas aos cargos dos funcionários, como restrições para registro de ponto e limites de horas extras.

---

# 🎯 Objetivos

Os principais objetivos do projeto são:

      1. Desenvolver uma API REST utilizando Spring Boot.
      2. Aplicar uma arquitetura organizada em camadas.
      3. Separar entidades, DTOs, regras de negócio e controllers.
      4. Implementar persistência utilizando JPA/Hibernate.
      5. Utilizar PostgreSQL como banco de dados.
      6. Implementar autenticação e autorização.
      7. Utilizar OAuth 2.0 Authorization Code.
      8. Utilizar PKCE para proteger o fluxo de autorização.
      9. Utilizar JWT para autenticação das requisições.
      10. Implementar controle de acesso baseado em roles.
      11. Implementar validações de entrada.
      12. Centralizar o tratamento de exceções.
      13. Implementar paginação nos endpoints de consulta.
      14. Documentar a API utilizando OpenAPI/Swagger.
      15. Aplicar regras de negócio relacionadas à jornada de trabalho.

---

# 🛠️ Tecnologias utilizadas
      
      | Tecnologia                  | Utilização                        |
      | --------------------------- | --------------------------------- |
      | Java 26                     | Linguagem principal               |
      | Spring Boot 4.1.1           | Framework principal               |
      | Spring Web MVC              | Desenvolvimento da API REST       |
      | Spring Data JPA             | Persistência e acesso ao banco    |
      | Hibernate                   | ORM                               |
      | Spring Security             | Segurança da aplicação            |
      | Spring Authorization Server | Servidor de autorização OAuth 2.0 |
      | OAuth 2.0                   | Autenticação/autorização          |
      | JWT                         | Tokens de acesso                  |
      | PostgreSQL                  | Banco de dados                    |
      | Bean Validation             | Validação dos dados               |
      | BCrypt                      | Criptografia das senhas           |
      | SpringDoc OpenAPI           | Documentação da API               |
      | Swagger UI                  | Testes e exploração dos endpoints |
      | Maven                       | Gerenciamento de dependências     |

---

# 🏗️ Arquitetura

O projeto utiliza uma arquitetura baseada em camadas, separando as responsabilidades da aplicação.

```text
      src/
      └── main/
          ├── java/
          │   └── com.portal.recursos.humanos/
          │
          │       ├── configurations/
          │       ├── controllers/
          │       ├── entities/
          │       ├── enums/
          │       ├── exceptions/
          │       ├── repositories/
          │       ├── requests/
          │       ├── responses/
          │       ├── security/
          │       └── services/
          │
          └── resources/
              └── application.properties
```

## 📂 Principais pacotes

### `entities`

Contém as entidades persistidas no banco de dados.

Principais entidades:

      * `Funcionario`
      * `RegistroPonto`

---

### `repositories`

Responsável pelo acesso aos dados através do Spring Data JPA.

Principais repositories:

      * `FuncionarioRepository`
      * `RegistroPontoRepository`

---

### `services`

Concentra as regras de negócio da aplicação.

Principais serviços:

      * `FuncionarioService`
      * `RegistroPontoService`
      * `RelatorioHorasService`
      * `CustomUserDetailsService`

---

### `controllers`

Responsáveis pela exposição dos endpoints HTTP.

Principais controllers:

      * `FuncionarioController`
      * `RegistroPontoController`
      * `RelatorioHorasController`

---

### `requests`

Contém os DTOs utilizados para receber dados das requisições.

Exemplos:

      * `FuncionarioRequest`
      * `RegistroPontoRequest`
      * `AtualizarSenhaRequest`

---

### `responses`

Contém os DTOs utilizados nas respostas da API.

Exemplos:

      * `FuncionarioResponse`
      * `RegistroPontoResponse`
      * `RelatorioHorasResponse`

As entidades não são expostas diretamente pelos controllers.

---

### `exceptions`

Centraliza as exceções específicas da aplicação e o tratamento global através de:

```text
   GlobalExceptionHandler
```

Também existe um modelo padronizado para respostas de erro:

```text
   ErrorResponse
```

---

### `security`

Contém componentes relacionados às regras de autorização.

Principais classes:

      * `AdministradorSecurity`
      * `FuncionarioSecurity`
      * `CustomAuthenticationEntryPoint`

---

### `configurations`

Contém as configurações de infraestrutura da aplicação.

Principais configurações:

      * `SecurityConfiguration`
      * `AuthorizationServerConfiguration`
      * `EncoderConfiguration`
      * `OpenAPIConfiguration`

---

# 👨‍💼 Funcionários

A entidade `Funcionario` possui os seguintes dados:

```text
      UUID id
      String nome
      CargoFuncionario cargo
      NivelDeAcesso role
      String senha
```

O identificador utiliza `UUID`.

O nome do funcionário é único no banco de dados.

As senhas não são armazenadas em texto puro. Antes da persistência, são processadas utilizando `BCryptPasswordEncoder`.

---

# 👔 Cargos disponíveis

A aplicação possui os seguintes cargos:

```text
      GERENTE
      COORDENADOR
      ANALISTA
      ASSISTENTE
      ESTAGIARIO
```

O cargo influencia diretamente algumas regras relacionadas ao registro de ponto e às horas extras.

---

# 🔐 Níveis de acesso

São utilizados dois níveis de acesso:

```text
      ROLE_ADMIN
      ROLE_USER
```

### ROLE_ADMIN

Possui permissões administrativas, incluindo operações como:

      * cadastrar funcionários;
      * listar funcionários;
      * atualizar funcionários;
      * excluir funcionários;
      * excluir registros de ponto;
      * administrar dados protegidos.

### ROLE_USER

Possui acesso limitado aos próprios dados.

A aplicação utiliza `FuncionarioSecurity` para verificar se o usuário autenticado está tentando acessar ou alterar os dados correspondentes ao próprio funcionário.

---

# 🔑 Segurança

A API utiliza **Spring Security** juntamente com **OAuth 2.0 Authorization Server**.

O fluxo implementado utiliza:

```text
      Authorization Code
              +
      PKCE
              +
      JWT
```

A arquitetura de segurança possui três responsabilidades principais:

```text
      Authorization Server
              ↓
      emite o Access Token
              ↓
      Resource Server
              ↓
      valida o JWT
              ↓
      API REST
```

---

# 🔄 Fluxo de autenticação

O fluxo pode ser representado da seguinte maneira:

```text
      Cliente
         │
         │ 1. Solicita autorização
         ▼
      Authorization Server
         │
         │ 2. Login
         ▼
      Funcionário
         │
         │ 3. Autoriza
         ▼
      Authorization Server
         │
         │ 4. Authorization Code
         ▼
      Cliente
         │
         │ 5. Troca Code + PKCE
         ▼
      Token Endpoint
         │
         │ 6. Access Token JWT
         ▼
      Cliente
         │
         │ 7. Authorization: Bearer <token>
         ▼
      API REST
         │
         │ 8. Validação do JWT
         ▼
      Endpoint protegido
```

---

# 🪪 JWT e Roles

O Access Token possui informações relacionadas às roles do usuário.

Durante a criação do JWT, as authorities que possuem o prefixo:

```text
      ROLE_
```

são adicionadas ao claim:

```json
      {
        "roles": [
          "ROLE_ADMIN"
        ]
      }
```

A API posteriormente converte essas informações em `GrantedAuthority`.

Isso permite utilizar as roles durante a autorização dos endpoints.

---

# 🔒 Controle de autorização

O projeto utiliza autorização em diferentes níveis.

Por exemplo:

```java
      @PreAuthorize("@administradorsecurity.somenteAdmin(...)")
```

para operações administrativas.

Também existe uma verificação específica para garantir que um usuário comum não consiga acessar ou modificar dados de outro funcionário:

```java
      @PreAuthorize("@funcionarioSecurity.verificarPodeAlterar(#id, authentication)")
```

Dessa maneira, as regras de autorização permanecem separadas das regras de negócio.

---

# 👤 CRUD de Funcionários

## Criar funcionário

```http
      POST /api/funcionarios
```

Exemplo:

```json
      {
        "nome": "João da Silva",
        "cargo": "ANALISTA",
        "role": "ROLE_USER",
        "senha": "123456"
      }
```

Resposta:

```text
      201 Created
```

Somente administradores podem cadastrar novos funcionários.

---

## Listar funcionários

```http
      GET /api/funcionarios
```

A consulta utiliza paginação.

Configuração padrão:

```text
      size = 8
      sort = nome
```

Exemplo:

```http
      GET /api/funcionarios?page=0&size=8&sort=nome,asc
```

---

## Buscar funcionário

```http
      GET /api/funcionarios/{id}
```

Exemplo:

```http
      GET /api/funcionarios/550e8400-e29b-41d4-a716-446655440000
```

---

## Atualizar funcionário

```http
      PUT /api/funcionarios/{id}
```

Exemplo:

```json
      {
        "nome": "João da Silva",
        "cargo": "COORDENADOR",
        "role": "ROLE_USER",
        "senha": "123456"
      }
```

---

## Alterar senha

```http
      PATCH /api/funcionarios/{id}
```

Exemplo:

```json
      {
        "senhaAtual": "123456",
        "novaSenha": "654321"
      }
```

A aplicação verifica a senha atual antes de permitir a alteração.

A nova senha também é armazenada utilizando BCrypt.

---

## Excluir funcionário

```http
      DELETE /api/funcionarios/{id}
```

Resposta:

```text
      204 No Content
```

Existe uma regra de integridade:

> Um funcionário que possui registros de ponto associados não pode ser excluído diretamente.

Nesse cenário, a API retorna um erro de conflito.

---

# ⏱️ Registro de Ponto

Os registros de ponto são associados a um funcionário através de relacionamento:

```text
      Funcionario
           │
           │ 1:N
           ▼
      RegistroPonto
```

Cada registro possui:

```text
      UUID id
      LocalDate data
      LocalTime horarioEntrada
      LocalTime horarioSaida
      Funcionario funcionario
```

---

# 📋 Regras de registro de ponto

A aplicação implementa diversas regras de negócio.

### 1. Funcionário precisa existir

Não é possível registrar ponto para um funcionário inexistente.

---

### 2. Gerentes não registram ponto

Funcionários com cargo:

```text
      GERENTE
```

não podem registrar ponto.

---

### 3. Estagiários não registram ponto

Funcionários com cargo:

```text
      ESTAGIARIO
```

também não podem registrar ponto.

---

### 4. Horário mínimo de entrada

A entrada não pode ser anterior às:

```text
      06:00
```

---

### 5. Horário máximo de saída

A saída não pode ultrapassar:

```text
      22:00
```

---

### 6. Saída deve ser posterior à entrada

A aplicação impede registros como:

```text
      Entrada: 18:00
      Saída:   17:00
```

---

### 7. Apenas um registro por dia

O funcionário não pode possuir mais de um registro de ponto para a mesma data.

---

# 🕐 Cálculo de horas trabalhadas

A aplicação considera automaticamente uma hora de intervalo para almoço.

A fórmula utilizada é:

```text
      Horas trabalhadas =
      horário de saída
      -
      horário de entrada
      -
      1 hora de intervalo
```

Por exemplo:

```text
      Entrada: 08:00
      Saída:   17:00
      
      Tempo total:       9 horas
      Intervalo:         1 hora
      Horas trabalhadas: 8 horas
```

---

# ⏰ Cálculo de horas extras

A jornada padrão considerada pela aplicação é:

```text
      8 horas por dia
```

Quando o funcionário ultrapassa oito horas trabalhadas, o excedente é considerado hora extra.

Exemplo:

```text
      Horas trabalhadas: 10h
      Jornada padrão:     8h
      
      Horas extras:       2h
```

---

# 📈 Limite de horas extras

Os limites variam de acordo com o cargo.

      | Cargo       |      Limite diário |
      | ----------- | -----------------: |
      | ANALISTA    |            3 horas |
      | ASSISTENTE  |            3 horas |
      | COORDENADOR |            5 horas |
      | GERENTE     | Não registra ponto |
      | ESTAGIARIO  | Não registra ponto |

Caso o limite seja ultrapassado, o registro não é realizado.

---

# 📝 Registrar ponto

```http
      POST /api/funcionarios/{funcionarioId}/pontos
```

Exemplo:

```json
      {
        "data": "2026-09-15",
        "horarioEntrada": "08:00:00",
        "horarioSaida": "18:00:00"
      }
```

A resposta contém informações calculadas:

```json
      {
        "id": "...",
        "funcionarioId": "...",
        "nomeFuncionario": "João da Silva",
        "data": "2026-09-15",
        "horarioEntrada": "08:00:00",
        "horarioSaida": "18:00:00",
        "horasTrabalhadas": "PT9H",
        "horasExtras": "PT1H"
      }
```

Resposta:

```text
      201 Created
```

---

# 📚 Consultar registros de ponto

```http
      GET /api/funcionarios/{funcionarioId}/pontos
```

O endpoint utiliza paginação.

Configuração padrão:

```text
      size = 5
      sort = data
      direction = DESC
```

Exemplo:

```http
      GET /api/funcionarios/{id}/pontos?page=0&size=5&sort=data,desc
```

Os registros mais recentes são apresentados primeiro pela configuração padrão.

---

# 🗑️ Excluir histórico de ponto

Administradores podem excluir todos os registros de ponto de um funcionário:

```http
      DELETE /api/funcionarios/{funcionarioId}/pontos
```

---

# 🗑️ Excluir registro específico

Também é possível excluir um registro individual:

```http
      DELETE /api/funcionarios/{funcionarioId}/pontos/{registroId}
```

A aplicação verifica se o registro realmente pertence ao funcionário informado na URL antes de realizar a exclusão.

---

# 📊 Relatório mensal

A API possui um endpoint específico para geração de relatório mensal:

```http
      GET /api/funcionarios/{funcionarioId}/pontos/relatorio
```

Parâmetros:

```text
      mes
      ano
```

Exemplo:

```http
      GET /api/funcionarios/{id}/pontos/relatorio?mes=9&ano=2026
```

O relatório retorna:

      * ID do funcionário;
      * nome do funcionário;
      * mês;
      * ano;
      * quantidade de dias trabalhados;
      * total de horas trabalhadas;
      * total de horas extras.

Exemplo:

```json
      {
        "funcionarioId": "...",
        "nomeFuncionario": "João da Silva",
        "mes": 9,
        "ano": 2026,
        "diasTrabalhados": 20,
        "horasTrabalhadas": "PT160H",
        "horasExtras": "PT12H"
      }
```

---

# 📑 Documentação com Swagger

A API utiliza **SpringDoc OpenAPI** para gerar automaticamente a documentação.

O Swagger possui informações sobre:
      
      * endpoints;
      * parâmetros;
      * DTOs;
      * respostas;
      * códigos HTTP;
      * autenticação;
      * OAuth 2.0;
      * Authorization Code;
      * PKCE.

A documentação pode ser acessada através de:

```text
      http://localhost:8080/swagger-ui/index.html
```

O fluxo OAuth2 foi configurado para permitir autenticação diretamente pelo Swagger UI.

---

# 🔐 Swagger + OAuth 2.0 + PKCE

O Swagger está configurado para utilizar:

```text
      Authorization Code
      +
      PKCE
```

Configuração utilizada:

```properties
      springdoc.swagger-ui.oauth.client-id=portal-rh-client
      springdoc.swagger-ui.oauth.client-secret=portal-rh-secret
      springdoc.swagger-ui.oauth.use-pkce-with-authorization-code-grant=true
      springdoc.swagger-ui.oauth.use-basic-authentication-with-access-code-grant=true
```

Após autenticar no Swagger, o Access Token é utilizado automaticamente nas requisições protegidas.

---

# 🗄️ Banco de dados

O projeto utiliza PostgreSQL.

As configurações são obtidas através de variáveis de ambiente:

```properties
      spring.datasource.url=${DB_URL}
      spring.datasource.username=${DB_USERNAME}
      spring.datasource.password=${DB_PASSWORD}
```

Exemplo:

```text
      DB_URL=jdbc:postgresql://localhost:5432/portal_rh
      DB_USERNAME=postgres
      DB_PASSWORD=sua_senha
```

O Hibernate está configurado para atualizar automaticamente a estrutura das tabelas:

```properties
      spring.jpa.hibernate.ddl-auto=update
```

---

# 🧱 Modelo de dados

A aplicação possui duas entidades principais:

```text
      ┌──────────────────────┐
      │      Funcionario     │
      ├──────────────────────┤
      │ id                   │
      │ nome                 │
      │ cargo                │
      │ role                 │
      │ senha                │
      └──────────┬───────────┘
                 │
                 │ 1:N
                 │
                 ▼
      ┌──────────────────────┐
      │    RegistroPonto     │
      ├──────────────────────┤
      │ id                   │
      │ data                 │
      │ horarioEntrada       │
      │ horarioSaida         │
      │ funcionario_id       │
      └──────────────────────┘
```

O relacionamento é implementado através de:

```java
      @ManyToOne(fetch = FetchType.LAZY)
```

---

# ⚠️ Tratamento de exceções

A aplicação utiliza um tratamento global através de:

```java
      @RestControllerAdvice
```

Implementado em:

```text
      GlobalExceptionHandler
```

Isso permite que os erros sejam retornados de forma padronizada.

Modelo:

```json
      {
        "timestamp": "2026-09-16T02:00:00",
        "status": 404,
        "error": "Not Found",
        "message": "Funcionário não encontrado com ID: ..."
      }
```

---

# 📋 Principais exceções de negócio

O projeto possui exceções específicas para diferentes situações:

```text
      FuncionarioNaoEncontradoException
      FuncionarioJaCadastradoException
      FuncionarioComRegistroPontoException
      FuncionarioNaoBatePontoException
      RegistroPontoDuplicadoException
      RegistroPontoNaoEncontradoException
      RegistroPontoFuncionarioIncompativelException
      HorarioInvalidoException
      HoraExtraExcedidaException
      SenhaAtualIncorretaException
      OperacaoNaoAutorizadaException
```

Essa abordagem evita concentrar todas as regras em exceções genéricas e torna o código mais expressivo.

---

# 📦 DTOs

A aplicação utiliza DTOs para controlar os dados de entrada e saída.

### Request

```text
      FuncionarioRequest
      RegistroPontoRequest
      AtualizarSenhaRequest
```

### Response

```text
      FuncionarioResponse
      RegistroPontoResponse
      RelatorioHorasResponse
```

Isso evita expor diretamente as entidades JPA através da API.

Além disso, a senha não faz parte do `FuncionarioResponse`.

---

# ✅ Validação

Os dados recebidos pelos endpoints são validados utilizando Jakarta Bean Validation.

Exemplo:

```java
      @NotBlank
```

```java
      @NotNull
```

```java
      @Size(min = 6)
```

Os controllers utilizam:

```java
      @Valid
```

para ativar automaticamente essas validações.

---

# 📄 Paginação

Os endpoints de listagem utilizam o mecanismo de paginação do Spring Data.

Funcionários:

```text
      GET /api/funcionarios
```

Configuração padrão:

```text
      size = 8
      sort = nome
```

Registros de ponto:

```text
      GET /api/funcionarios/{funcionarioId}/pontos
```

Configuração padrão:

```text
      size = 5
      sort = data
      direction = DESC
```

A utilização de `Pageable` evita retornar grandes quantidades de dados em uma única requisição.

---

# 🔄 Fluxo de uma requisição

A estrutura geral da aplicação segue:

```text
      HTTP Request
           │
           ▼
      Controller
           │
           ▼
      Request DTO
           │
           ▼
      Validation
           │
           ▼
      Service
           │
           ├── Regras de negócio
           ├── Autorização
           └── Processamento
           │
           ▼
      Repository
           │
           ▼
      PostgreSQL
           │
           ▼
      Entity
           │
           ▼
      Response DTO
           │
           ▼
      HTTP Response
```

---

# 🧪 Testes

O projeto possui estrutura de testes utilizando o ecossistema de testes do Spring Boot.

As dependências de teste utilizadas incluem:

```text
      spring-boot-starter-data-jpa-test
      spring-boot-starter-validation-test
      spring-boot-starter-webmvc-test
```

Os testes acompanham a evolução das camadas da aplicação, permitindo validar comportamento de persistência, serviços e controllers conforme as funcionalidades são implementadas.

---

# 🚀 Como executar o projeto

## 1. Clonar o repositório

```bash
      git clone <URL_DO_REPOSITORIO>
```

Entrar na pasta:

```bash
      cd portalderecursoshumanos
```

---

## 2. Configurar o PostgreSQL

Criar um banco de dados:

```sql
      CREATE DATABASE portal_rh;
```

---

## 3. Configurar as variáveis de ambiente

Linux/macOS:

```bash
      export DB_URL=jdbc:postgresql://localhost:5432/portal_rh
      export DB_USERNAME=postgres
      export DB_PASSWORD=sua_senha
```

Windows PowerShell:

```powershell
      $env:DB_URL="jdbc:postgresql://localhost:5432/portal_rh"
      $env:DB_USERNAME="postgres"
      $env:DB_PASSWORD="sua_senha"
```

---

## 4. Executar com Maven

```bash
      ./mvnw spring-boot:run
```

No Windows:

```powershell
      .\mvnw.cmd spring-boot:run
```

Ou através da IDE executando:

```text
      PortalDeRecursosHumanosApplication
```

---

# 🌐 URLs principais

Após iniciar a aplicação:

### API

```text
      http://localhost:8080
```

### Swagger UI

```text
      http://localhost:8080/swagger-ui/index.html
```

### OpenAPI JSON

```text
      http://localhost:8080/v3/api-docs
```

### Authorization Endpoint

```text
      http://localhost:8080/oauth2/authorize
```

### Token Endpoint

```text
      http://localhost:8080/oauth2/token
```

### JWK Set

```text
      http://localhost:8080/oauth2/jwks
```

---

# 🔑 Client OAuth2

Para ambiente de desenvolvimento, o Authorization Server possui um cliente configurado:

```text
      Client ID:
      portal-rh-client
```

```text
      Client Secret:
      portal-rh-secret
```

O cliente utiliza:

```text
      Authorization Code
      Refresh Token
      PKCE
```

### Redirect URI — Swagger

```text
      http://localhost:8080/swagger-ui/oauth2-redirect.html
```

### Redirect URI — Postman

```text
      https://oauth.pstmn.io/v1/callback
```

> ⚠️ Essas credenciais estão configuradas para o ambiente de desenvolvimento. Em um ambiente de produção, o client secret não deve permanecer exposto no código-fonte e as credenciais devem ser externalizadas através de variáveis de ambiente ou outro mecanismo seguro de configuração.

---

# 🔐 Exemplo de requisição autenticada

Depois de obter o Access Token:

```http
      Authorization: Bearer <ACCESS_TOKEN>
```

Exemplo:

```http
      GET /api/funcionarios
      Authorization: Bearer eyJhbGciOiJSUzI1NiJ9...
```

O Resource Server valida o JWT antes de permitir o acesso ao endpoint.

---

# 📡 Principais endpoints

## Funcionários

      | Método | Endpoint                 | Descrição             |
      | ------ | ------------------------ | --------------------- |
      | POST   | `/api/funcionarios`      | Cadastrar funcionário |
      | GET    | `/api/funcionarios`      | Listar funcionários   |
      | GET    | `/api/funcionarios/{id}` | Buscar funcionário    |
      | PUT    | `/api/funcionarios/{id}` | Atualizar funcionário |
      | PATCH  | `/api/funcionarios/{id}` | Alterar senha         |
      | DELETE | `/api/funcionarios/{id}` | Excluir funcionário   |

---

## Registro de ponto

      | Método | Endpoint                                     | Descrição         |
      | ------ | -------------------------------------------- | ----------------- |
      | POST   | `/api/funcionarios/{id}/pontos`              | Registrar ponto   |
      | GET    | `/api/funcionarios/{id}/pontos`              | Listar registros  |
      | DELETE | `/api/funcionarios/{id}/pontos`              | Excluir histórico |
      | DELETE | `/api/funcionarios/{id}/pontos/{registroId}` | Excluir registro  |

---

## Relatórios

      | Método | Endpoint                                  | Descrição              |
      | ------ | ----------------------------------------- | ---------------------- |
      | GET    | `/api/funcionarios/{id}/pontos/relatorio` | Gerar relatório mensal |

---

# 📊 Códigos HTTP utilizados

A API utiliza códigos HTTP para representar o resultado das operações.

      | Código | Significado                                   |
      | -----: | --------------------------------------------- |
      |  `200` | Operação realizada com sucesso                |
      |  `201` | Recurso criado com sucesso                    |
      |  `204` | Operação realizada sem conteúdo de resposta   |
      |  `400` | Dados da requisição inválidos                 |
      |  `401` | Não autenticado                               |
      |  `403` | Acesso negado                                 |
      |  `404` | Recurso não encontrado                        |
      |  `409` | Conflito com o estado atual do recurso        |
      |  `422` | Regra de negócio não permitiu o processamento |

As respostas de erro relacionadas às regras da aplicação seguem o modelo:

```json
      {
        "timestamp": "...",
        "status": 400,
        "error": "...",
        "message": "..."
      }
```

---

# 🧩 Princípios aplicados

Durante o desenvolvimento foram aplicados conceitos importantes de desenvolvimento backend:

### Separação de responsabilidades

Controllers não concentram regras de negócio.

As regras ficam nos services.

---

### DTO Pattern

Requests e responses são separados das entidades persistentes.

---

### Repository Pattern

O acesso ao banco é abstraído pelos repositories do Spring Data JPA.

---

### Service Layer

As regras de negócio são centralizadas nos services.

---

### Exception Handling

Exceções específicas são tratadas por um mecanismo global.

---

### Validation

Dados de entrada são validados antes de serem processados.

---

### Authentication

A autenticação é realizada utilizando OAuth 2.0 e JWT.

---

### Authorization

As permissões são controladas através de roles e regras específicas de acesso.

---

### Pagination

Consultas de listagem utilizam `Pageable`.

---

### Password Security

As senhas são armazenadas utilizando BCrypt.

---

# 🧠 Regras de negócio implementadas

O projeto não é apenas um CRUD tradicional.

As principais regras implementadas incluem:

```text
      ✔ Nome do funcionário não pode ser duplicado
      
      ✔ Senha deve possuir pelo menos 6 caracteres
      
      ✔ Senhas são armazenadas utilizando BCrypt
      
      ✔ Apenas administradores podem cadastrar funcionários
      
      ✔ Apenas administradores podem atualizar dados administrativos
      
      ✔ Usuários comuns podem acessar apenas seus próprios dados
      
      ✔ Gerentes não registram ponto
      
      ✔ Estagiários não registram ponto
      
      ✔ Entrada não pode ser anterior às 06:00
      
      ✔ Saída não pode ultrapassar 22:00
      
      ✔ Saída deve ser posterior à entrada
      
      ✔ Apenas um registro de ponto por funcionário/dia
      
      ✔ Uma hora de intervalo é descontada da jornada
      
      ✔ Jornada padrão considerada: 8 horas
      
      ✔ Analistas possuem limite de 3h extras/dia
      
      ✔ Assistentes possuem limite de 3h extras/dia
      
      ✔ Coordenadores possuem limite de 5h extras/dia
      
      ✔ Funcionário com ponto associado não pode ser excluído
      
      ✔ Registro de ponto deve pertencer ao funcionário informado
      
      ✔ Alteração de senha exige validação da senha atual
```

---

# 🏆 Destaques técnicos

Este projeto demonstra conhecimentos em:

```text
      Java
      Spring Boot
      Spring MVC
      Spring Data JPA
      Hibernate
      PostgreSQL
      Spring Security
      OAuth 2.0
      Authorization Server
      Resource Server
      JWT
      PKCE
      BCrypt
      Bean Validation
      DTOs
      REST API
      Exception Handling
      Pagination
      OpenAPI
      Swagger
      JPA Relationships
      Business Rules
```

---

# 🔭 Possíveis evoluções

O projeto possui uma base que pode ser expandida futuramente com funcionalidades como:

      * Histórico de alterações;
      * Auditoria;
      * Férias;
      * Folgas;
      * Banco de horas;
      * Justificativas de ausência;
      * Afastamentos;
      * Departamentos;
      * Benefícios;
      * Gestão de cargos;
      * Relatórios exportáveis;
      * Exportação para PDF;
      * Exportação para Excel;
      * Notificações;
      * Integração com frontend;
      * Docker;
      * Docker Compose;
      * Testes de integração mais abrangentes;
      * CI/CD;
      * Deploy em ambiente cloud;
      * Persistência dos clientes OAuth2 em banco de dados.

---

# 👨‍💻 Autor

**Rodrigo**

Projeto desenvolvido como parte do processo de estudo e aprofundamento em desenvolvimento backend com **Java e Spring Boot**, explorando desde a construção de APIs REST e regras de negócio até mecanismos de autenticação e autorização utilizando **OAuth 2.0, JWT e Spring Security**.

---

# 📄 Licença

Este projeto foi desenvolvido para fins educacionais e de demonstração de conhecimentos em desenvolvimento backend.
