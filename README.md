# 🏢 Portal de Recursos Humanos

API REST desenvolvida com **Java e Spring Boot** para simular um sistema de gerenciamento de funcionários, registro de ponto, controle de jornada, horas extras e geração de relatórios mensais.

O projeto foi desenvolvido com foco em **boas práticas de desenvolvimento de APIs REST**, separação de responsabilidades, aplicação de regras de negócio, persistência relacional, validação de dados, tratamento de exceções e autenticação/autorização utilizando **Spring Security e OAuth 2.0**.

---

## 📋 Sobre o projeto

O **Portal de Recursos Humanos** representa uma API para gerenciamento das principais informações relacionadas à jornada de trabalho de funcionários.

A aplicação permite:
    
    * cadastrar funcionários;
    * consultar funcionários;
    * buscar um funcionário específico;
    * atualizar dados de funcionários;
    * alterar senha;
    * excluir funcionários;
    * registrar entrada e saída;
    * consultar o histórico de ponto;
    * excluir registros de ponto;
    * calcular horas trabalhadas;
    * calcular horas extras;
    * aplicar limites de horas extras conforme o cargo;
    * impedir registros de ponto para determinados cargos;
    * gerar relatórios mensais de jornada;
    * controlar acesso por nível de usuário;
    * autenticar usuários utilizando OAuth 2.0;
    * emitir tokens JWT;
    * validar tokens JWT nas requisições protegidas;
    * transportar as roles do usuário dentro do JWT;
    * aplicar autorização baseada em roles e no próprio funcionário;
    * retornar respostas padronizadas para erros da API.

O projeto também utiliza **paginação** nas consultas de funcionários e registros de ponto.

---

# 🎯 Objetivos

O principal objetivo do projeto é desenvolver uma API REST que simule um sistema de RH, permitindo aplicar na prática conceitos importantes do ecossistema Java/Spring.

Entre os principais objetivos técnicos estão:

    * desenvolver APIs REST utilizando Spring Boot;
    * aplicar arquitetura em camadas;
    * utilizar Spring Data JPA;
    * trabalhar com PostgreSQL;
    * utilizar UUID como identificador;
    * utilizar DTOs para entrada e saída de dados;
    * implementar validações;
    * aplicar regras de negócio na camada de serviço;
    * trabalhar com relacionamentos JPA;
    * implementar paginação;
    * criar tratamento global de exceções;
    * aplicar autenticação e autorização;
    * implementar OAuth 2.0 Authorization Server;
    * utilizar JWT para autenticação das APIs;
    * implementar controle de acesso baseado em roles;
    * proteger dados de um funcionário contra acesso indevido por outros usuários;
    * desenvolver testes automatizados;
    * organizar o projeto seguindo princípios de separação de responsabilidades.

---

# 🛠️ Tecnologias utilizadas

    | Tecnologia                  | Utilização                                      |
    | --------------------------- | ----------------------------------------------- |
    | Java 26                     | Linguagem principal                             |
    | Spring Boot 4.1.1           | Framework principal                             |
    | Spring Web MVC              | Desenvolvimento da API REST                     |
    | Spring Data JPA             | Persistência e acesso ao banco                  |
    | Hibernate                   | ORM                                             |
    | PostgreSQL                  | Banco de dados relacional                       |
    | Spring Validation           | Validação dos dados de entrada                  |
    | Spring Security             | Segurança da aplicação                          |
    | Spring Authorization Server | Implementação do OAuth 2.0 Authorization Server |
    | JWT                         | Tokens de acesso                                |
    | BCrypt                      | Criptografia de senhas                          |
    | Maven                       | Gerenciamento de dependências e build           |
    | Postman                     | Testes e validação das requisições HTTP         |
    | JUnit                       | Testes automatizados                            |
    | Mockito                     | Testes unitários com mocks                      |

---

# 🏗️ Arquitetura

A aplicação segue uma arquitetura em camadas, separando as responsabilidades de cada componente.

```text
    Controller
        ↓
    Service
        ↓
    Repository
        ↓
    Database
```

Além dessas camadas, o projeto possui estruturas específicas para:

```text
    Entities
    Requests
    Responses
    Exceptions
    Configurations
    Security
    Enums
```

Estrutura simplificada:

```text
    src
    ├── main
    │   ├── java
    │   │   └── com.portal.recursos.humanos
    │   │       ├── configurations
    │   │       ├── controllers
    │   │       ├── entities
    │   │       ├── enums
    │   │       ├── exceptions
    │   │       ├── repositories
    │   │       ├── requests
    │   │       ├── responses
    │   │       ├── security
    │   │       └── services
    │   │
    │   └── resources
    │       └── application.properties
    │
    └── test
        └── java
```

---

# 📦 Estrutura das principais camadas

## Controllers

Responsáveis por receber as requisições HTTP e encaminhá-las para os serviços.

Atualmente existem:
    
    * `FuncionarioController`
    * `RegistroPontoController`
    * `RelatorioHorasController`

Os controllers não concentram as regras de negócio. Essa responsabilidade pertence à camada de serviço.

---

## Services

Concentram as regras de negócio da aplicação.

Principais serviços:

### `FuncionarioService`

Responsável pelo gerenciamento dos funcionários.

Entre suas responsabilidades estão:

    * cadastro;
    * consulta;
    * atualização;
    * alteração de senha;
    * exclusão;
    * verificação de funcionário duplicado;
    * criptografia de senha;
    * impedimento da exclusão de funcionários que possuem registros de ponto;
    * aplicação das regras de autorização.

### `RegistroPontoService`

Responsável pelo gerenciamento dos registros de ponto.

Implementa regras como:

    * funcionário deve existir;
    * determinados cargos não batem ponto;
    * horário de entrada mínimo;
    * horário de saída máximo;
    * saída deve ser posterior à entrada;
    * não permitir dois registros para o mesmo funcionário no mesmo dia;
    * calcular horas extras;
    * limitar horas extras de acordo com o cargo;
    * consultar histórico de ponto;
    * excluir registros.

### `RelatorioHorasService`

Responsável pela geração do relatório mensal de jornada.

O serviço:

    1. verifica se o funcionário existe;
    2. determina o período utilizando `YearMonth`;
    3. obtém o primeiro e o último dia do mês;
    4. consulta os registros do período;
    5. soma as horas trabalhadas;
    6. soma as horas extras;
    7. contabiliza os dias trabalhados;
    8. retorna um `RelatorioHorasResponse`.

---

# 👤 Funcionários

A entidade `Funcionario` representa os colaboradores cadastrados no sistema.

Principais atributos:

```text
    id
    nome
    cargo
    role
    senha
```

O identificador utiliza `UUID`.

O nome é configurado como único no banco de dados.

A senha não é armazenada em texto puro. Antes da persistência, ela passa pelo `PasswordEncoder`, utilizando BCrypt.

---

# 🏷️ Cargos

Os cargos disponíveis são definidos pelo enum `CargoFuncionario`.

```java
    GERENTE
    COORDENADOR
    ANALISTA
    ASSISTENTE
    ESTAGIARIO
```

Os cargos também participam diretamente das regras de negócio do registro de ponto.

---

# 🔐 Níveis de acesso

O projeto utiliza duas roles:

```java
    ROLE_ADMIN
    ROLE_USER
```

### `ROLE_ADMIN`

Possui permissões administrativas, incluindo operações como:

    * cadastrar funcionários;
    * listar funcionários;
    * atualizar funcionários;
    * excluir funcionários;
    * excluir histórico de ponto;
    * excluir registros individuais.

### `ROLE_USER`

Possui acesso restrito aos próprios dados, de acordo com as regras de segurança da aplicação.

Um usuário comum não deve conseguir acessar ou modificar informações pertencentes a outro funcionário.

---

# ⏱️ Registro de ponto

A entidade `RegistroPonto` representa o registro diário de jornada.

Relacionamento:

```text
    Funcionario 1 ───────── N RegistroPonto
```

Cada registro possui:

```text
    id
    data
    horarioEntrada
    horarioSaida
    funcionario
```

O relacionamento é implementado com:

```java
    @ManyToOne(fetch = FetchType.LAZY)
```

e:

```java
    @JoinColumn(name = "funcionario_id", nullable = false)
```

---

# 📐 Regras de negócio do ponto

Uma das partes mais importantes do projeto está na aplicação das regras de jornada.

## Horário mínimo de entrada

O funcionário não pode registrar entrada antes das:

```text
    06:00
```

---

## Horário máximo de saída

O horário de saída não pode ultrapassar:

```text
    22:00
```

---

## Saída posterior à entrada

O sistema verifica se:

```text
    horarioSaida > horarioEntrada
```

Registros inválidos são rejeitados.

---

## Intervalo para almoço

A aplicação considera automaticamente:

```text
    1 hora de almoço
```

O cálculo é realizado subtraindo uma hora do intervalo entre entrada e saída.

Exemplo:

```text
    Entrada:  08:00
    Saída:    18:00
    
    Tempo total: 10 horas
    Almoço:      1 hora
    -------------------
    Trabalhado:  9 horas
```

---

# 🕐 Jornada padrão

A jornada padrão utilizada pelo sistema é:

```text
    8 horas por dia
```

Quando o funcionário trabalha além das oito horas, o excedente é considerado hora extra.

Exemplo:

```text
    Horas trabalhadas: 9h
    Jornada padrão:    8h
    
    Horas extras:      1h
```

---

# 📊 Limite de horas extras

O sistema também aplica limites diferentes conforme o cargo.

### Analista

Máximo:

```text
    3 horas extras por dia
```

### Assistente

Máximo:

```text
    3 horas extras por dia
```

### Coordenador

Máximo:

```text
    5 horas extras por dia
```

Os cargos que não possuem essa regra específica não recebem o mesmo limite.

---

# 🚫 Funcionários que não batem ponto

Determinados cargos não podem registrar ponto.

Atualmente:

```text
    GERENTE
    ESTAGIARIO
```

Quando um funcionário desses cargos tenta registrar um ponto, a aplicação lança uma exceção específica:

```text
    FuncionarioNaoBatePontoException
```

---

# 🔁 Registro duplicado

O sistema impede que o mesmo funcionário tenha mais de um registro de ponto na mesma data.

A verificação é realizada diretamente pelo repository:

```java
    existsByFuncionarioIdAndData(...)
```

Caso já exista um registro para aquele dia, a operação é rejeitada.

---

# 📑 Relatório mensal de horas

A API também possui um recurso para geração de relatório mensal.

Endpoint:

```http
    GET /api/funcionarios/{funcionarioId}/pontos/relatorio?mes=9&ano=2026
```

O relatório contém:

```text
    funcionarioId
    nomeFuncionario
    mes
    ano
    diasTrabalhados
    horasTrabalhadas
    horasExtras
```

Exemplo conceitual:

```json
    {
      "funcionarioId": "UUID",
      "nomeFuncionario": "João da Silva",
      "mes": 9,
      "ano": 2026,
      "diasTrabalhados": 20,
      "horasTrabalhadas": "PT160H",
      "horasExtras": "PT12H"
    }
```

O período é calculado utilizando `YearMonth`, permitindo determinar corretamente o primeiro e o último dia de cada mês.

---

# 📄 DTOs

A aplicação utiliza objetos específicos para entrada e saída de dados.

## Requests

### `FuncionarioRequest`

Utilizado para receber:

```text
    nome
    cargo
    role
    senha
```

Possui validações como:

    * nome obrigatório;
    * cargo obrigatório;
    * role obrigatória;
    * senha obrigatória;
    * senha com pelo menos 6 caracteres.

### `RegistroPontoRequest`

Recebe:

```text
    data
    horarioEntrada
    horarioSaida
```

Todos os campos são obrigatórios.

### `AtualizarSenhaRequest`

Utilizado para alteração segura da senha.

---

# 📤 Responses

As entidades não são expostas diretamente nas respostas principais da API.

São utilizados DTOs como:

```text
    FuncionarioResponse
    RegistroPontoResponse
    RelatorioHorasResponse
```

Essa abordagem evita acoplamento direto entre a estrutura de persistência e o contrato da API.

---

# 📄 Paginação

As consultas de funcionários e registros de ponto utilizam paginação através do Spring Data.

Funcionários:

```http
    GET /api/funcionarios/listar
```

Configuração padrão:

```text
    size = 8
    sort = nome
```

Registros de ponto:

```http
    GET /api/funcionarios/{funcionarioId}/pontos/listar
```

Configuração padrão:

```text
    size = 5
    sort = data
    direction = DESC
```

A paginação é realizada utilizando `Pageable`, `Page` e `@PageableDefault`.

Isso evita retornar grandes quantidades de registros de uma única vez.

---

# 🌐 Endpoints

## Funcionários

### Cadastrar funcionário

```http
    POST /api/funcionarios/registrar
```

Resposta:

```text
    201 Created
```

---

### Listar funcionários

```http
    GET /api/funcionarios/listar
```

Resposta:

```text
    200 OK
```

Retorna uma página de funcionários.

---

### Buscar funcionário

```http
    GET /api/funcionarios/buscar/{id}
```

Resposta:

```text
    200 OK
```

---

### Atualizar funcionário

```http
    PUT /api/funcionarios/atualizar/{id}
```

Resposta:

```text
    200 OK
```

---

### Alterar senha

```http
    PATCH /api/funcionarios/alterar-senha/{id}
```

Resposta:

```text
    200 OK
```

---

### Excluir funcionário

```http
    DELETE /api/funcionarios/deletar/{id}
```

Resposta:

```text
    204 No Content
```

A exclusão é bloqueada quando existem registros de ponto associados ao funcionário.

---

# ⏰ Endpoints de ponto

## Registrar ponto

```http
    POST /api/funcionarios/{funcionarioId}/pontos/registrar
```

Resposta:

```text
    201 Created
```

---

## Listar pontos

```http
    GET /api/funcionarios/{funcionarioId}/pontos/listar
```

Resposta:

```text
    200 OK
```

Utiliza paginação.

---

## Excluir histórico de ponto

```http
    DELETE /api/funcionarios/{funcionarioId}/pontos/deletar
```

Resposta:

```text
    204 No Content
```

Operação administrativa.

---

## Excluir registro específico

```http
    DELETE /api/funcionarios/{funcionarioId}/pontos/deletar/{registroId}
```

Resposta:

```text
    204 No Content
```

Operação administrativa.

---

# 📊 Endpoint de relatório

## Relatório mensal

```http
    GET /api/funcionarios/{funcionarioId}/pontos/relatorio?mes=9&ano=2026
```

Resposta:

```text
    200 OK
```

O relatório consolida os registros do funcionário no mês solicitado.

---

# 🔒 Segurança

A segurança da aplicação utiliza **Spring Security** em conjunto com **Spring Authorization Server**.

O projeto possui dois fluxos relacionados:

```text
    OAuth 2.0 Authorization Server
                ↓
              JWT
                ↓
    Resource Server / API
                ↓
    Autorização
```

---

# 🔑 OAuth 2.0 Authorization Server

A aplicação possui um Authorization Server responsável por emitir tokens.

O cliente configurado é:

```text
    Client ID:
    portal-rh-client
```

O segredo configurado para o cliente é:

```text
    portal-rh-secret
```

O projeto utiliza:

```text
    Authorization Code
    Refresh Token
    PKCE
```

O cliente exige Proof Key for Code Exchange:

```text
    requireProofKey = true
```

O redirect URI configurado para os testes com Postman é:

```text
    https://oauth.pstmn.io/v1/callback
```

---

# 🎫 JWT

Após a autenticação, o Authorization Server emite um JWT.

Além das informações padrão do token, o projeto adiciona as roles do usuário através de um `OAuth2TokenCustomizer`.

Exemplo:

```json
    {
      "sub": "Administrador",
      "aud": "portal-rh-client",
      "roles": [
        "ROLE_ADMIN"
      ],
      "iss": "http://localhost:8080"
    }
```

A API utiliza essas informações para determinar as permissões do usuário.

---

# 🛡️ Resource Server

A API funciona como Resource Server e valida os tokens JWT recebidos.

As requisições protegidas devem utilizar:

```http
    Authorization: Bearer <token>
```

O `JwtAuthenticationConverter` transforma as roles presentes no claim:

```text
    roles
```

em autoridades do Spring Security.

Dessa forma:

```text
    ROLE_ADMIN
```

e:

```text
    ROLE_USER
```

passam a ser reconhecidas pelo mecanismo de autorização.

---

# 👮 Controle de acesso

Além da autorização baseada em role, o projeto possui uma camada específica chamada `FuncionarioSecurity`.

Ela verifica se um usuário comum está tentando acessar dados pertencentes a ele próprio.

Conceitualmente:

```text
    ROLE_ADMIN
        ↓
    pode acessar dados de funcionários
    
    ROLE_USER
        ↓
    pode acessar seus próprios dados
        ↓
    não pode acessar dados de outro funcionário
```

Isso permite separar:

* regras de negócio;
* autenticação;
* autorização;
* controle de propriedade dos dados.

---

# 🔐 Criptografia de senhas

As senhas são protegidas utilizando:

```text
    BCryptPasswordEncoder
```

A senha recebida no cadastro não é armazenada diretamente.

Fluxo:

```text
    Senha recebida
          ↓
    PasswordEncoder
          ↓
    BCrypt
          ↓
    Senha criptografada
          ↓
    PostgreSQL
```

Durante a alteração da senha, a aplicação também verifica a senha atual antes de permitir a substituição.

---

# ⚠️ Tratamento global de exceções

A aplicação possui um `GlobalExceptionHandler` implementado com `@RestControllerAdvice`.

Dessa forma, exceções de negócio são convertidas em respostas HTTP padronizadas.

Entre as exceções implementadas estão:

    * `FuncionarioNaoEncontradoException`
    * `FuncionarioJaCadastradoException`
    * `FuncionarioComRegistroPontoException`
    * `FuncionarioNaoBatePontoException`
    * `RegistroPontoDuplicadoException`
    * `RegistroPontoNaoEncontradoException`
    * `RegistroPontoFuncionarioIncompativelException`
    * `HorarioInvalidoException`
    * `HoraExtraExcedidaException`
    * `OperacaoNaoAutorizadaException`
    * `SenhaAtualIncorretaException`

---

# 📦 Resposta padronizada de erro

Os erros de negócio são representados pelo `ErrorResponse`.

A estrutura contém informações como:

```json
    {
      "timestamp": "2026-09-14T00:00:00",
      "status": 404,
      "error": "Not Found",
      "message": "Funcionário não encontrado com ID: ..."
    }
```

Isso proporciona um contrato consistente para os clientes da API.

---

# 🗄️ Banco de dados

O projeto utiliza **PostgreSQL**.

A configuração do banco não está diretamente exposta no código.

São utilizadas variáveis de ambiente:

```text
    DB_URL
    DB_USERNAME
    DB_PASSWORD
```

Configuração:

```properties
    spring.datasource.url=${DB_URL}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}
```

Essa abordagem evita deixar credenciais diretamente no código-fonte.

---

# 🧩 Persistência

A camada de persistência utiliza:

```text
    Spring Data JPA
    +
    Hibernate
    +
    PostgreSQL
```

Os principais repositories são:

```text
    FuncionarioRepository
    RegistroPontoRepository
```

O Spring Data permite utilizar métodos derivados, como:

```java
    findByNome(...)
    existsByNome(...)
    existsByFuncionarioIdAndData(...)
    findByFuncionarioId(...)
    findByFuncionarioIdAndDataBetween(...)
```

Sem a necessidade de escrever manualmente consultas SQL para essas operações.

---

# 🔗 Relacionamento entre entidades

O relacionamento principal é:

```text
    Funcionario
         │
         │ 1
         │
         │ N
         ▼
    RegistroPonto
```

Um funcionário pode possuir vários registros de ponto.

Cada registro de ponto pertence obrigatoriamente a um funcionário.

---

# 🧪 Testes

O projeto foi desenvolvido seguindo uma abordagem incremental, com testes sendo utilizados durante a implementação das regras de negócio e dos endpoints.

A estratégia inclui testes para:

    * cadastro de funcionários;
    * listagem;
    * busca;
    * atualização;
    * exclusão;
    * funcionários inexistentes;
    * registros de ponto;
    * horários inválidos;
    * cargos que não batem ponto;
    * registros duplicados;
    * limites de horas extras;
    * cálculo de horas trabalhadas;
    * cálculo de horas extras;
    * consulta paginada;
    * histórico de ponto;
    * relatórios mensais;
    * regras de autorização;
    * autenticação.

O projeto utiliza principalmente:

```text
    JUnit
    Mockito
    Spring Boot Test
```

---

# 📮 Postman

O projeto possui uma estrutura de arquivos do Postman para facilitar a execução e validação dos endpoints.

As requisições incluem operações relacionadas a:

```text
    Funcionários
    Registro de ponto
    Autenticação OAuth2
    JWKS
    Atualização de senha
```

O fluxo OAuth2 pode ser testado utilizando o Authorization Code com PKCE através do Postman.

---

# ⚙️ Configuração do ambiente

## Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

    * Java 26;
    * Maven, caso não seja utilizado o Maven Wrapper;
    * PostgreSQL;
    * Postman, caso queira testar manualmente as requisições.

---

# 🗃️ Configuração do PostgreSQL

Crie um banco de dados para o projeto.

Por exemplo:

```sql
    CREATE DATABASE portal_rh;
```

Depois configure as variáveis de ambiente:

```text
    DB_URL=jdbc:postgresql://localhost:5432/portal_rh
    DB_USERNAME=seu_usuario
    DB_PASSWORD=sua_senha
```

Os valores devem ser configurados de acordo com o ambiente local.

---

# ▶️ Executando o projeto

O projeto possui Maven Wrapper.

No Windows:

```bash
    mvnw.cmd spring-boot:run
```

No Linux/macOS:

```bash
    ./mvnw spring-boot:run
```

Ou utilizando Maven instalado:

```bash
    mvn spring-boot:run
```

A aplicação será iniciada por padrão em:

```text
    http://localhost:8080
```

---

# 🔐 Fluxo de autenticação

O fluxo simplificado para acessar os endpoints protegidos é:

```text
    1. Cliente solicita autorização
              ↓
    2. Authorization Server
              ↓
    3. Login do usuário
              ↓
    4. Authorization Code
              ↓
    5. Token Endpoint
              ↓
    6. Access Token JWT
              ↓
    7. Requisição para a API
              ↓
    8. Bearer Token
              ↓
    9. Validação do JWT
              ↓
    10. Verificação da role/permissão
              ↓
    11. Acesso ao recurso
```

---

# 📡 Exemplo de requisição

Depois de obter um token:

```http
    GET http://localhost:8080/api/funcionarios/listar
    Authorization: Bearer SEU_ACCESS_TOKEN
```

Um usuário com:

```text
    ROLE_ADMIN
```

pode acessar a listagem.

Um usuário com:

```text
    ROLE_USER
```

recebe:

```text
    403 Forbidden
```

quando não possui autorização para a operação.

Uma requisição sem autenticação recebe:

```text
    401 Unauthorized
```

---

# 🧠 Principais conceitos aplicados

Este projeto reúne diversos conceitos importantes do desenvolvimento backend com Java:

### Java

    * Classes e objetos;
    * Records;
    * Enums;
    * UUID;
    * `Duration`;
    * `LocalDate`;
    * `LocalTime`;
    * `YearMonth`;
    * Streams;
    * Optional;
    * exceções customizadas.

### Spring Boot

    * Injeção de dependência;
    * Beans;
    * Controllers;
    * Services;
    * Repositories;
    * configuração por annotations;
    * validação;
    * tratamento global de exceções.

### Spring Data JPA

    * Entidades;
    * `@Id`;
    * geração de UUID;
    * `@ManyToOne`;
    * `FetchType.LAZY`;
    * `@JoinColumn`;
    * repositories;
    * queries derivadas;
    * paginação.

### Spring Security

    * autenticação;
    * autorização;
    * roles;
    * `@PreAuthorize`;
    * `UserDetailsService`;
    * `DaoAuthenticationProvider`;
    * `PasswordEncoder`;
    * BCrypt;
    * JWT;
    * Resource Server.

### OAuth 2.0

    * Authorization Server;
    * Authorization Code;
    * Refresh Token;
    * PKCE;
    * Client ID;
    * Client Secret;
    * Redirect URI;
    * JWT Access Token;
    * JWK/JWKS.

---

# 🧱 Princípios e boas práticas utilizados

O projeto procura manter uma separação clara entre as responsabilidades.

### Controller

Responsável pela comunicação HTTP.

### Service

Responsável pelas regras de negócio.

### Repository

Responsável pela persistência.

### Entity

Representa os dados persistidos.

### Request

Representa os dados recebidos pela API.

### Response

Define os dados retornados pela API.

### Security

Concentra regras relacionadas a autenticação e autorização.

### Exception

Centraliza as exceções específicas da aplicação.

Essa divisão facilita:

    * manutenção;
    * testes;
    * evolução;
    * reutilização;
    * compreensão do código;
    * redução de acoplamento.

---

# 📈 Evolução do projeto

O projeto foi construído de forma incremental.

A implementação seguiu uma sequência semelhante a:

```text
    Configuração inicial
            ↓
    Entidade Funcionário
            ↓
    Repository
            ↓
    Service
            ↓
    Controller
            ↓
    DTOs
            ↓
    Validações
            ↓
    Registro de ponto
            ↓
    Regras de jornada
            ↓
    Horas extras
            ↓
    Paginação
            ↓
    Tratamento de exceções
            ↓
    Relatórios
            ↓
    Spring Security
            ↓
    OAuth 2.0
            ↓
    JWT
            ↓
    Controle de autorização
```

Essa abordagem permitiu implementar e validar cada camada antes de adicionar novos níveis de complexidade.

---

# 🚀 Possíveis evoluções futuras

Embora o projeto já possua uma estrutura completa para uma API de estudo e portfólio, algumas funcionalidades podem ser adicionadas futuramente.

## 📧 Identificação por e-mail

Adicionar e-mail ao funcionário e utilizá-lo como identificador de login, evitando utilizar o nome como username.

---

## ✏️ Edição de registros de ponto

Adicionar:

```http
    PUT /api/funcionarios/{funcionarioId}/pontos/{registroId}
```

com regras específicas para alteração de registros.

---

## 🏖️ Férias

Implementar um módulo para:

    * solicitação de férias;
    * aprovação;
    * períodos;
    * histórico;
    * validação de conflitos.

---

## 📝 Auditoria

Registrar:

    * usuário responsável;
    * operação realizada;
    * data/hora;
    * recurso alterado;
    * informações relevantes da operação.

---

## 📚 Documentação com OpenAPI

Adicionar Swagger/OpenAPI para documentar:

    * endpoints;
    * parâmetros;
    * requests;
    * responses;
    * códigos HTTP;
    * autenticação.

---

## 🐳 Docker

Containerizar:

```text
    API
    PostgreSQL
```

permitindo executar o ambiente completo com Docker Compose.

---

## 🧪 Testcontainers

Utilizar containers reais para testes de integração com PostgreSQL, reduzindo a diferença entre o ambiente de teste e o banco utilizado em produção.

---

# ⚠️ Considerações sobre o projeto

Este projeto foi desenvolvido principalmente como **projeto de estudo e portfólio**, tendo como objetivo demonstrar conhecimentos de desenvolvimento backend com Java e Spring.

Algumas configurações, como o cliente OAuth2 armazenado em memória e a geração da chave RSA durante a execução, são adequadas para o ambiente de desenvolvimento/estudo, mas deveriam ser revistas em um ambiente produtivo.

Em uma aplicação real, seria recomendável utilizar:

    * armazenamento persistente dos clientes OAuth2;
    * gerenciamento seguro de secrets;
    * chaves RSA persistentes e protegidas;
    * HTTPS;
    * gerenciamento adequado de sessões/tokens;
    * logs e auditoria;
    * monitoramento;
    * configurações separadas por ambiente;
    * infraestrutura de produção.

---

# 📁 Principais pacotes

```text
    com.portal.recursos.humanos
    │
    ├── configurations
    │   ├── AuthorizationServerConfiguration
    │   ├── EncoderConfiguration
    │   └── SecurityConfiguration
    │
    ├── controllers
    │   ├── FuncionarioController
    │   ├── RegistroPontoController
    │   └── RelatorioHorasController
    │
    ├── entities
    │   ├── Funcionario
    │   └── RegistroPonto
    │
    ├── enums
    │   ├── CargoFuncionario
    │   └── NivelDeAcesso
    │
    ├── exceptions
    │   ├── ErrorResponse
    │   ├── GlobalExceptionHandler
    │   └── exceções de negócio
    │
    ├── repositories
    │   ├── FuncionarioRepository
    │   └── RegistroPontoRepository
    │
    ├── requests
    │   ├── AtualizarSenhaRequest
    │   ├── FuncionarioRequest
    │   └── RegistroPontoRequest
    │
    ├── responses
    │   ├── FuncionarioResponse
    │   ├── RegistroPontoResponse
    │   └── RelatorioHorasResponse
    │
    ├── security
    │   ├── AdministradorSecurity
    │   ├── CustomAuthenticationEntryPoint
    │   └── FuncionarioSecurity
    │
    └── services
        ├── CustomUserDetailsService
        ├── FuncionarioService
        ├── RegistroPontoService
        └── RelatorioHorasService
```

---

# 📌 Resumo dos recursos

    | Recurso                            | Implementado |
    | ---------------------------------- | :----------: |
    | CRUD de funcionários               |       ✅      |
    | UUID                               |       ✅      |
    | PostgreSQL                         |       ✅      |
    | Spring Data JPA                    |       ✅      |
    | DTOs                               |       ✅      |
    | Bean Validation                    |       ✅      |
    | Registro de ponto                  |       ✅      |
    | Regras de jornada                  |       ✅      |
    | Intervalo de almoço                |       ✅      |
    | Cálculo de horas trabalhadas       |       ✅      |
    | Cálculo de horas extras            |       ✅      |
    | Limite de horas extras por cargo   |       ✅      |
    | Bloqueio de ponto por cargo        |       ✅      |
    | Registro duplicado                 |       ✅      |
    | Paginação                          |       ✅      |
    | Relatório mensal de horas          |       ✅      |
    | Exceções customizadas              |       ✅      |
    | Global Exception Handler           |       ✅      |
    | BCrypt                             |       ✅      |
    | Spring Security                    |       ✅      |
    | Roles ADMIN/USER                   |       ✅      |
    | Controle de acesso por funcionário |       ✅      |
    | OAuth 2.0 Authorization Server     |       ✅      |
    | Authorization Code                 |       ✅      |
    | Refresh Token                      |       ✅      |
    | PKCE                               |       ✅      |
    | JWT                                |       ✅      |
    | JWT com roles                      |       ✅      |
    | Resource Server                    |       ✅      |
    | Postman                            |       ✅      |

---

# 👨‍💻 Autor

**Rodrigo Marques Viana**

Projeto desenvolvido para estudo, prática e demonstração de conhecimentos em desenvolvimento backend com **Java, Spring Boot, Spring Security, OAuth 2.0, JWT, JPA e PostgreSQL**.

---

# ⭐ Considerações finais

O **Portal de Recursos Humanos** foi desenvolvido para representar uma API backend próxima de um cenário real, indo além de operações básicas de CRUD.

O projeto combina **persistência, regras de negócio, validações, paginação, tratamento de exceções, segurança, autenticação OAuth 2.0, JWT e autorização baseada em roles**, proporcionando uma aplicação com diferentes níveis de complexidade.

Além de demonstrar conhecimento das principais ferramentas do ecossistema Spring, o projeto também evidencia uma preocupação com **organização arquitetural, separação de responsabilidades, segurança e evolução incremental da aplicação**.
