# 🏨 Hotel Reservas API

API REST desenvolvida em **Java com Spring Boot** para gerenciamento de reservas de hotel, usuários e autenticação.

O projeto foi desenvolvido com foco na construção de uma aplicação backend organizada em camadas, aplicando conceitos de **REST, Spring Data JPA, PostgreSQL, DTOs, Mapper, Bean Validation, tratamento global de exceções e Spring Security com autenticação JWT**.

Além das operações de CRUD, a aplicação implementa diferentes níveis de autorização por meio das roles `USER` e `ADMIN`, protegendo as operações de acordo com suas respectivas responsabilidades.

---

## 📌 Sobre o projeto

O **Hotel Reservas API** simula o backend de um sistema de gerenciamento de reservas hoteleiras.

A aplicação permite:

    * cadastrar e consultar reservas;
    * atualizar reservas;
    * excluir reservas;
    * ordenar reservas pelo período de estadia;
    * cadastrar usuários;
    * consultar usuários;
    * atualizar dados de usuários;
    * alterar senhas;
    * realizar reset administrativo de senha;
    * remover usuários;
    * autenticar usuários;
    * proteger endpoints através de JWT;
    * controlar permissões utilizando `USER` e `ADMIN`;
    * validar dados recebidos pela API;
    * retornar respostas padronizadas para erros.

A aplicação utiliza **PostgreSQL** como banco de dados e **Spring Data JPA/Hibernate** para persistência.

---

# 🎯 Objetivos do projeto

O projeto foi desenvolvido para consolidar conhecimentos de desenvolvimento backend utilizando Java e Spring Boot.

Entre os principais objetivos estão:

    * desenvolver uma API REST completa;
    * aplicar arquitetura em camadas;
    * trabalhar com Spring Boot;
    * utilizar Spring Data JPA;
    * integrar a aplicação com PostgreSQL;
    * implementar autenticação com Spring Security;
    * implementar autenticação baseada em JWT;
    * trabalhar com autorização baseada em roles;
    * utilizar DTOs para entrada e saída de dados;
    * aplicar o padrão Mapper;
    * utilizar Bean Validation;
    * implementar tratamento global de exceções;
    * utilizar UUID como identificador;
    * utilizar `BigDecimal` para valores monetários;
    * trabalhar com `enum`;
    * documentar a API utilizando OpenAPI/Swagger;
    * aplicar separação de responsabilidades;
    * desenvolver regras de negócio independentes da camada HTTP.

---

# 🛠️ Tecnologias utilizadas

    | Tecnologia              | Utilização                              |
    | ----------------------- | --------------------------------------- |
    | Java 26                 | Linguagem principal                     |
    | Spring Boot 4.1.1       | Framework principal                     |
    | Spring Web MVC          | Construção da API REST                  |
    | Spring Data JPA         | Persistência de dados                   |
    | Hibernate               | ORM                                     |
    | PostgreSQL              | Banco de dados                          |
    | Spring Security         | Autenticação e autorização              |
    | JWT                     | Autenticação baseada em tokens          |
    | JJWT 0.13.0             | Criação e validação dos tokens JWT      |
    | Bean Validation         | Validação dos dados                     |
    | BCrypt                  | Hash das senhas                         |
    | SpringDoc OpenAPI 3.1.1 | Documentação Swagger                    |
    | Maven                   | Gerenciamento do projeto e dependências |
    | UUID                    | Identificação dos recursos              |

---

# 🏗️ Arquitetura

O projeto utiliza uma arquitetura organizada por responsabilidades.

```text
    src
    └── main
        ├── java
        │   └── com.hotel.reservas
        │       │
        │       ├── configurations
        │       ├── controllers
        │       ├── dto
        │       ├── entities
        │       ├── enums
        │       ├── exceptions
        │       ├── mapper
        │       ├── repositories
        │       └── services
        │
        └── resources
            └── application.properties
```

O fluxo principal de uma operação é:

```text
    Cliente
       │
       ▼
    Controller
       │
       ▼
    Service
       │
       ▼
    Repository
       │
       ▼
    PostgreSQL
```

Quando a requisição exige autenticação:

```text
    Cliente
       │
       │ Authorization: Bearer JWT
       ▼
    Spring Security
       │
       ▼
    JwtAuthenticationFilter
       │
       ▼
    JwtService
       │
       ▼
    CustomUserDetailsService
       │
       ▼
    PostgreSQL
       │
       ▼
    SecurityContext
       │
       ▼
    Authorization
       │
       ▼
    Controller
       │
       ▼
    Service
       │
       ▼
    Repository
```

---

# 📂 Organização das camadas

## `controllers`

Responsáveis pela camada HTTP da aplicação.

Recebem as requisições, validam os dados recebidos e encaminham as operações para os Services.

Controllers existentes:

```text
    AuthController
    ReservaController
    UsuarioController
```

---

## `services`

Concentram as operações e regras de negócio.

Principais Services:

```text
    AuthService
    ReservaService
    UsuarioService
    JwtService
    CustomUserDetailsService
```

A camada Service também é responsável por aplicar as regras de autorização específicas de cada operação.

---

## `repositories`

Responsáveis pelo acesso aos dados utilizando Spring Data JPA.

```text
    ReservaRepository
    UsuarioRepository
```

---

## `entities`

Representam as entidades persistidas no banco de dados.

```text
    Reserva
    Usuario
```

---

## `dto`

Define os objetos utilizados na comunicação entre cliente e API.

### Reservas

```text
    ReservaRequestDTO
    ReservaResponseDTO
```

### Usuários

```text
    CriarUsuarioDTO
    UsuarioRequestDTO
    UsuarioResponseDTO
```

### Autenticação

```text
    LoginRequestDTO
    LoginResponseDTO
```

### Senhas

```text
    AlterarSenhaDTO
    ResetarSenhaDTO
```

---

## `mapper`

Responsável pela conversão entre DTOs e entidades.

```text
    ReservaMapper
    UsuarioMapper
```

Exemplo conceitual:

```text
    ReservaRequestDTO
           │
           ▼
    ReservaMapper
           │
           ▼
    Reserva
```

E no retorno:

```text
    Reserva
       │
       ▼
    ReservaMapper
       │
       ▼
    ReservaResponseDTO
```

Essa abordagem evita que os Controllers e Services fiquem responsáveis por conversões repetitivas.

---

# 🏨 Modelo de Reserva

A entidade `Reserva` representa uma reserva realizada no hotel.

Principais atributos:

```text
    UUID id
    String nomeHospede
    TipoDeQuarto tipoDeQuarto
    int diasDeEstadia
    BigDecimal valorDiaria
```

---

## 💰 Cálculo do valor total

A entidade possui uma regra para calcular o valor total da reserva:

```text
    valorTotal = valorDiaria × diasDeEstadia
```

Exemplo:

```text
    Diária: R$ 250,00
    Estadia: 5 dias
    
    Total:
    250 × 5 = R$ 1.250,00
```

O cálculo utiliza `BigDecimal`, apropriado para representar valores monetários.

---

# 🛏️ Tipos de quarto

Os tipos de quarto são representados através de `enum`.

```java
    STANDARD
    LUXO
    PRESIDENCIAL
```

Isso evita que valores arbitrários sejam enviados para o campo de tipo de quarto.

Exemplo válido:

```json
    {
      "nomeHospede": "João da Silva",
      "tipoDeQuarto": "STANDARD",
      "diasDeEstadia": 5,
      "valorDiaria": 250.00
    }
```

---

# 👤 Usuários

A entidade `Usuario` possui:

```text
    UUID id
    String email
    String senha
    Role role
```

O e-mail é único no banco de dados.

As roles disponíveis são:

```java
    USER
    ADMIN
```

---

# 🔐 Autenticação

A autenticação da aplicação é realizada através de **JWT — JSON Web Token**.

O fluxo de login é:

```text
    E-mail + senha
          │
          ▼
    AuthenticationManager
          │
          ▼
    DaoAuthenticationProvider
          │
          ▼
    CustomUserDetailsService
          │
          ▼
    PostgreSQL
          │
          ▼
    PasswordEncoder / BCrypt
          │
          ▼
    Usuário autenticado
          │
          ▼
    JwtService
          │
          ▼
    JWT
```

Após o login, o cliente recebe um token.

Exemplo:

```json
    {
      "token": "eyJ..."
    }
```

Esse token deve ser enviado nas próximas requisições:

```http
    Authorization: Bearer eyJ...
```

---

# 🔑 JWT

O projeto utiliza a biblioteca **JJWT 0.13.0** para criação e validação dos tokens.

O token contém informações como:

```text
    subject → e-mail do usuário
    role    → role do usuário
    issuedAt
    expiration
```

O `JwtService` é responsável por:

    * gerar tokens;
    * extrair claims;
    * extrair o e-mail;
    * validar o token;
    * verificar sua assinatura;
    * verificar sua validade temporal.

---

# 🛡️ JWT + consulta ao banco

Um detalhe importante da implementação é que a aplicação não utiliza exclusivamente a role armazenada no token para definir as permissões.

Quando o JWT chega:

```text
    JWT
     │
     ▼
    extrai e-mail
     │
     ▼
    CustomUserDetailsService
     │
     ▼
    busca usuário no PostgreSQL
     │
     ▼
    obtém role atual
     │
     ▼
    SecurityContext
```

Dessa forma, a aplicação consulta o estado atual do usuário no banco.

Por exemplo, se determinado usuário possuía:

```text
    ROLE_ADMIN
```

e posteriormente sua role foi alterada no banco para:

```text
    ROLE_USER
```

as próximas autenticações construídas pelo filtro utilizarão a autoridade atual encontrada no banco.

Isso permite que as regras de autorização estejam relacionadas ao estado atual do usuário.

---

# 🔒 Proteção de senhas

As senhas não são armazenadas em texto puro.

A aplicação utiliza:

```java
    BCryptPasswordEncoder
```

através da abstração:

```java
    PasswordEncoder
```

Fluxo:

```text
    Senha
      │
      ▼
    BCryptPasswordEncoder
      │
      ▼
    Hash
      │
      ▼
    PostgreSQL
```

Durante a autenticação, o Spring Security compara a senha informada com o hash armazenado.

---

# 👥 Autorização

A aplicação utiliza duas roles:

```text
    USER
    ADMIN
```

As operações possuem permissões diferentes.

## USER

Pode:

    * autenticar-se;
    * consultar reservas;
    * consultar usuários;
    * alterar seus próprios dados permitidos;
    * alterar sua própria senha.

## ADMIN

Além das operações disponíveis ao usuário comum, pode:

    * criar reservas;
    * atualizar reservas;
    * excluir reservas;
    * cadastrar usuários;
    * alterar dados de outros usuários;
    * resetar senhas;
    * remover usuários.

---

# 🛡️ Regras específicas de autorização

O projeto separa regras de autorização genéricas do fluxo de negócio.

### `AdministradorSecurity`

É responsável por verificar se o usuário possui:

```text
    ROLE_ADMIN
```

Exemplo utilizado:

```java
    @PreAuthorize("""
    @administradorsecurity.somenteAdmin(
        authentication,
        'Somente administradores podem criar reservas.'
    )""")
```

---

### `UsuarioSecurity`

É utilizado para verificar se o usuário pode alterar determinado usuário.

A regra é:

```text
    ADMIN
      │
      └── pode alterar outros usuários
    
    USER
      │
      └── pode alterar somente os próprios dados
```

---

# 🚫 Regra adicional para exclusão de usuários

Existe uma regra específica para impedir que um administrador exclua a própria conta.

Exemplo:

```text
    Administrador autenticado
            │
            ▼
    Tenta excluir a própria conta
            │
            ▼
    OperacaoNaoPermitidaException
```

Isso evita que o administrador remova a própria conta através dessa operação administrativa.

---

# 📋 API REST

## 🔑 Autenticação

### Login

```http
    POST /auth/login
```

Autentica o usuário e retorna um JWT.

### Request

```json
    {
      "email": "admin@email.com",
      "senha": "senha"
    }
```

### Response

```json
    {
      "token": "eyJ..."
    }
```

---

# 🏨 Reservas

Base:

```text
    /api/v1/reservas
```

---

## Criar reserva

```http
    POST /api/v1/reservas
```

**Permissão:** `ADMIN`

### Request

```json
    {
      "nomeHospede": "João da Silva",
      "tipoDeQuarto": "STANDARD",
      "diasDeEstadia": 5,
      "valorDiaria": 250.00
    }
```

### Response

```json
    {
      "idReserva": "UUID",
      "nomeHospede": "João da Silva",
      "tipoDeQuarto": "STANDARD",
      "diasDeEstadia": 5,
      "valorDiaria": 250.00
    }
```

### Status

```text
    201 Created
    400 Bad Request
    401 Unauthorized
    403 Forbidden
```

---

## Listar reservas

```http
    GET /api/v1/reservas
```

**Permissão:** `USER` ou `ADMIN`

As reservas são retornadas em ordem decrescente de dias de estadia.

Exemplo:

```text
    7 dias
    5 dias
    4 dias
    3 dias
    1 dia
```

### Status

```text
    200 OK
    401 Unauthorized
```

---

## Buscar reserva por ID

```http
    GET /api/v1/reservas/{id}
```

**Permissão:** `USER` ou `ADMIN`

### Status

```text
    200 OK
    400 Bad Request
    401 Unauthorized
    404 Not Found
```

---

## Atualizar reserva

```http
    PUT /api/v1/reservas/{id}
```

**Permissão:** `ADMIN`

### Status

```text
    200 OK
    400 Bad Request
    401 Unauthorized
    403 Forbidden
    404 Not Found
```

---

## Excluir reserva

```http
    DELETE /api/v1/reservas/{id}
```

**Permissão:** `ADMIN`

### Status

```text
    204 No Content
    401 Unauthorized
    403 Forbidden
    404 Not Found
```

---

# 👥 Usuários

Base:

```text
    /api/usuarios
```

---

## Cadastrar usuário

```http
    POST /api/usuarios
```

**Permissão:** `ADMIN`

O e-mail deve ser único.

### Status

```text
    201 Created
    400 Bad Request
    401 Unauthorized
    403 Forbidden
    409 Conflict
```

---

## Listar usuários

```http
    GET /api/usuarios
```

**Permissão:** usuário autenticado.

Retorna os usuários cadastrados utilizando `UsuarioResponseDTO`.

---

## Buscar usuário

```http
    GET /api/usuarios/{id}
```

**Permissão:** usuário autenticado.

### Status

```text
    200 OK
    401 Unauthorized
    404 Not Found
```

---

## Atualizar usuário

```http
    PUT /api/usuarios/{id}
```

A autorização é definida de acordo com o usuário autenticado:

```text
    ADMIN → pode atualizar outros usuários
    
    USER → pode atualizar somente a própria conta
```

Também existe validação para impedir duplicidade de e-mail.

---

## Alterar senha

```http
    PATCH /api/usuarios/{id}/alterar-senha
```

O usuário precisa informar a senha atual.

Fluxo:

```text
    Senha atual
         │
         ▼
    PasswordEncoder.matches()
         │
         ├── incorreta → erro
         │
         └── correta
               │
               ▼
         Nova senha
               │
               ▼
         BCrypt
               │
               ▼
         PostgreSQL
```

---

## Resetar senha

```http
    PATCH /api/usuarios/{id}/resetar-senha
```

**Permissão:** `ADMIN`

O administrador pode definir uma nova senha para outro usuário.

A nova senha é armazenada utilizando BCrypt.

---

## Remover usuário

```http
    DELETE /api/usuarios/{id}
```

**Permissão:** `ADMIN`

Existe uma regra adicional:

```text
    ADMIN não pode remover a própria conta.
```

Em caso de tentativa, a aplicação lança:

```text
    OperacaoNaoPermitidaException
```

---

# 📊 Matriz de autorização

    | Endpoint                                 |       USER      | ADMIN |
    | ---------------------------------------- | :-------------: | :---: |
    | `POST /auth/login`                       |        ✅        |   ✅   |
    | `POST /api/v1/reservas`                  |        ❌        |   ✅   |
    | `GET /api/v1/reservas`                   |        ✅        |   ✅   |
    | `GET /api/v1/reservas/{id}`              |        ✅        |   ✅   |
    | `PUT /api/v1/reservas/{id}`              |        ❌        |   ✅   |
    | `DELETE /api/v1/reservas/{id}`           |        ❌        |   ✅   |
    | `POST /api/usuarios`                     |        ❌        |   ✅   |
    | `GET /api/usuarios`                      |        ✅        |   ✅   |
    | `GET /api/usuarios/{id}`                 |        ✅        |   ✅   |
    | `PUT /api/usuarios/{id}`                 | Próprio usuário |   ✅   |
    | `PATCH /api/usuarios/{id}/alterar-senha` | Próprio usuário |   ✅   |
    | `PATCH /api/usuarios/{id}/resetar-senha` |        ❌        |   ✅   |
    | `DELETE /api/usuarios/{id}`              |        ❌        |   ✅   |

---

# ✅ Validação

A API utiliza **Jakarta Bean Validation**.

Exemplos:

```java
    @NotBlank
    private String nomeHospede;
```

```java
    @NotNull
    @Min(1)
    private int diasDeEstadia;
```

```java
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal valorDiaria;
```

Para usuários e autenticação também são aplicadas validações relacionadas aos dados recebidos.

A validação ocorre antes da execução da operação de negócio.

Fluxo:

```text
    Request
       │
       ▼
    Bean Validation
       │
       ├── inválido → 400
       │
       └── válido
            │
            ▼
         Service
```

---

# ⚠️ Tratamento global de exceções

A aplicação possui um `GlobalExceptionHandler` utilizando:

```java
    @RestControllerAdvice
```

Isso centraliza o tratamento de exceções.

Entre as exceções específicas utilizadas estão:

```text
    ReservaNaoEncontradaException
    UsuarioNaoEncontradoException
    EmailJaCadastradoException
    SenhaAtualIncorretaException
    OperacaoNaoPermitidaException
```

---

# 📄 Resposta padronizada de erro

Os erros são representados através de:

```text
    ErrorResponse
```

A estrutura contém informações como:

```text
    timestamp
    status
    error
    message
    path
```

Exemplo:

```json
    {
      "timestamp": "2026-09-16T01:00:00",
      "status": 404,
      "error": "Not Found",
      "message": "Reserva não encontrada com ID: ...",
      "path": "/api/v1/reservas/..."
    }
```

---

# 🔢 UUID

As entidades utilizam UUID como identificador.

Exemplo:

```text
    550e8400-e29b-41d4-a716-446655440000
```

A utilização de UUID evita a exposição direta de identificadores numéricos sequenciais.

---

# 💵 BigDecimal

Valores monetários utilizam:

```java
    BigDecimal
```

em vez de `double`.

Exemplo:

```java
    private BigDecimal valorDiaria;
```

Essa escolha é adequada para operações financeiras porque permite maior controle sobre precisão decimal.

---

# 🗄️ Banco de dados

O projeto utiliza:

```text
    PostgreSQL
```

A persistência é realizada através de:

```text
    Spring Data JPA
            +
    Hibernate
```

As principais tabelas são:

```text
    usuarios
    reservas
```

---

# ⚙️ Configuração

As informações de conexão com o banco e a chave JWT são obtidas através de variáveis de ambiente.

Exemplo:

```properties
    spring.datasource.url=${DB_URL}
    spring.datasource.username=${DB_USERNAME}
    spring.datasource.password=${DB_PASSWORD}
    
    jwt.secret=${JWT_SECRET}
    jwt.expiration=3600000
```

Isso evita colocar credenciais diretamente no código-fonte.

---

# 🔐 Variáveis de ambiente

Antes de executar a aplicação, configure:

```text
    DB_URL
    DB_USERNAME
    DB_PASSWORD
    JWT_SECRET
```

Exemplo conceitual:

```text
    DB_URL=jdbc:postgresql://localhost:5432/hotel_reservas
    DB_USERNAME=postgres
    DB_PASSWORD=sua_senha
    JWT_SECRET=sua_chave_secreta
```

> Não versione senhas, chaves JWT ou outras credenciais no repositório.

---

# 📖 Swagger / OpenAPI

A API possui documentação através do **SpringDoc OpenAPI**.

A configuração define:

```text
    Título:
    Hotel Reservations API
    
    Versão:
    1.0
    
    Autenticação:
    Bearer JWT
```

O esquema de segurança utilizado é:

```text
    bearerAuth
```

---

## Swagger UI

Com a aplicação executando localmente:

```text
    http://localhost:8080/swagger-ui/index.html
```

A documentação OpenAPI pode ser acessada através de:

```text
    http://localhost:8080/v3/api-docs
```

No Swagger, os endpoints protegidos podem utilizar o botão **Authorize** para informar o JWT.

Formato:

```text
    Bearer <TOKEN>
```

---

# 🔄 Fluxo completo de autenticação

```text
                        ┌───────────────┐
                        │    Cliente    │
                        └───────┬───────┘
                                │
                                │ POST /auth/login
                                ▼
                        ┌───────────────┐
                        │ AuthController│
                        └───────┬───────┘
                                │
                                ▼
                          AuthService
                                │
                                ▼
                     AuthenticationManager
                                │
                                ▼
                    DaoAuthenticationProvider
                                │
                                ▼
                   CustomUserDetailsService
                                │
                                ▼
                           PostgreSQL
                                │
                                ▼
                         PasswordEncoder
                                │
                                ▼
                           JwtService
                                │
                                ▼
                              JWT
                                │
                                ▼
                        ┌───────────────┐
                        │    Cliente    │
                        └───────┬───────┘
                                │
                      Authorization: Bearer
                                │
                                ▼
                     JwtAuthenticationFilter
                                │
                                ▼
                          JwtService
                                │
                                ▼
                   CustomUserDetailsService
                                │
                                ▼
                        SecurityContext
                                │
                                ▼
                        @PreAuthorize
                                │
                                ▼
                           Controller
```

---

# 🧪 Testes

O projeto possui estrutura de testes integrada ao Maven e às ferramentas de teste do ecossistema Spring Boot.

Os testes podem ser executados utilizando:

```bash
    ./mvnw test
```

No Windows:

```bash
    mvnw.cmd test
```

Para gerar o build:

```bash
    ./mvnw clean package
```

---

# ▶️ Como executar o projeto

## Pré-requisitos

É necessário possuir:
    
    * Java 26;
    * Maven ou Maven Wrapper;
    * PostgreSQL;
    * Git;
    * IDE de sua preferência.

---

## 1. Clonar o projeto

```bash
    git clone URL_DO_REPOSITORIO
```

Depois:

```bash
    cd hotel-reservas
```

---

## 2. Criar o banco PostgreSQL

Exemplo:

```sql
    CREATE DATABASE hotel_reservas;
```

---

## 3. Configurar as variáveis de ambiente

Configure:

```text
    DB_URL
    DB_USERNAME
    DB_PASSWORD
    JWT_SECRET
```

---

## 4. Executar a aplicação

Linux/macOS:

```bash
    ./mvnw spring-boot:run
```

Windows:

```bash
    mvnw.cmd spring-boot:run
```

Ou execute:

```text
    HotelReservasApplication
```

diretamente pela IDE.

---

# 🔎 Exemplo de utilização

## 1. Login

```http
    POST /auth/login
    Content-Type: application/json
```

```json
    {
      "email": "admin@email.com",
      "senha": "senha"
    }
```

A aplicação retorna:

```json
    {
      "token": "eyJ..."
    }
```

---

## 2. Utilizar o token

```http
    GET /api/v1/reservas
    Authorization: Bearer eyJ...
```

---

## 3. Criar uma reserva

```http
    POST /api/v1/reservas
    Authorization: Bearer eyJ...
    Content-Type: application/json
```

```json
    {
      "nomeHospede": "Maria Oliveira",
      "tipoDeQuarto": "LUXO",
      "diasDeEstadia": 4,
      "valorDiaria": 450.00
    }
```

---

# 📐 Princípios aplicados

O projeto busca aplicar princípios importantes de desenvolvimento backend.

## Separation of Concerns

Cada camada possui uma responsabilidade:

```text
    Controller
    → HTTP
    
    Service
    → Regras de negócio
    
    Repository
    → Persistência
    
    Mapper
    → Conversão entre objetos
    
    DTO
    → Contrato da API
    
    Configuration
    → Configurações e segurança
    
    Exception Handler
    → Tratamento de erros
```

---

## Dependency Injection

As dependências são fornecidas pelo Spring através de injeção de dependências.

Exemplo:

```java
    public ReservaService(
            ReservaRepository reservaRepository,
            ReservaMapper reservaMapper) {
    
        this.reservaRepository = reservaRepository;
        this.reservaMapper = reservaMapper;
    }
```

---

## Baixo acoplamento

A utilização de DTOs, Mappers, Services e Repositories reduz o acoplamento entre as diferentes partes da aplicação.

---

# 📚 Conhecimentos demonstrados

Este projeto reúne conhecimentos relacionados a:

### Java

    * Programação Orientada a Objetos;
    * classes;
    * interfaces;
    * enums;
    * UUID;
    * BigDecimal;
    * Records;
    * tratamento de exceções.

### Spring Boot

    * configuração;
    * injeção de dependências;
    * Controllers;
    * Services;
    * Beans;
    * configuração da aplicação.

### Spring Web

    * APIs REST;
    * HTTP methods;
    * `ResponseEntity`;
    * status codes;
    * `@RequestBody`;
    * `@PathVariable`;
    * validação de requisições.

### Spring Data JPA

    * entidades;
    * repositories;
    * `JpaRepository`;
    * consultas derivadas;
    * persistência.

### Spring Security

    * autenticação;
    * autorização;
    * roles;
    * `Authentication`;
    * `SecurityContext`;
    * `@PreAuthorize`;
    * `DaoAuthenticationProvider`;
    * `PasswordEncoder`;
    * BCrypt;
    * filtros de segurança.

### JWT

    * geração de tokens;
    * claims;
    * assinatura;
    * expiração;
    * validação;
    * autenticação stateless.

### API Design

    * DTOs;
    * Mapper;
    * tratamento global de exceções;
    * respostas HTTP;
    * documentação OpenAPI;
    * separação de responsabilidades.

---

# 🚀 Possíveis evoluções

O projeto pode ser expandido futuramente com funcionalidades como:

    * testes unitários mais abrangentes;
    * testes de integração;
    * testes de segurança;
    * Docker;
    * Docker Compose;
    * CI/CD;
    * paginação;
    * filtros de reservas;
    * cadastro de quartos;
    * controle de disponibilidade;
    * check-in;
    * check-out;
    * cancelamento de reservas;
    * datas de entrada e saída;
    * auditoria;
    * logs estruturados;
    * refresh tokens;
    * revogação de tokens;
    * integração com serviços externos;
    * deploy em ambiente cloud.

Essas funcionalidades representam possíveis extensões e não fazem parte da implementação atual.

---

# 🎓 Objetivo de aprendizado

O projeto foi desenvolvido como uma aplicação prática para consolidar conhecimentos de desenvolvimento backend com Java e Spring Boot.

A proposta evolui de um CRUD tradicional para uma API que incorpora diferentes conceitos encontrados em aplicações reais:

```text
    CRUD
     │
     ├── Spring Boot
     │
     ├── Spring Data JPA
     │
     ├── PostgreSQL
     │
     ├── DTOs
     │
     ├── Mapper
     │
     ├── Bean Validation
     │
     ├── Exception Handling
     │
     ├── Spring Security
     │
     ├── BCrypt
     │
     ├── JWT
     │
     ├── Roles
     │
     ├── Authorization
     │
     └── OpenAPI / Swagger
```

O resultado é uma API REST estruturada em camadas, com autenticação, autorização, persistência relacional, validação e documentação.

---

# 👨‍💻 Autor

**Rodrigo Marques Viana**

Projeto desenvolvido para estudo, prática e consolidação de conhecimentos em desenvolvimento backend utilizando **Java, Spring Boot, Spring Security, JPA e PostgreSQL**.

---

# 📄 Licença

Projeto desenvolvido para fins de estudo e aprendizado.
