# Books API

API REST para gerenciamento de livros.

## Tecnologias

- Java 21
- Spring Boot 4
- Spring Web MVC
- Spring Data JPA
- Spring Security
- Flyway
- PostgreSQL
- JWT
- SpringDoc OpenAPI
- Scalar
- Testcontainers
- RestAssured
- JUnit 5

## Funcionalidades atuais

- Cadastro de usuario
- Login com geracao de token JWT
- Cadastro de autor
- Listagem de autores
- Busca de autor por nome
- Documentacao interativa da API

## Requisitos

- Java 21
- Maven Wrapper
- PostgreSQL para execucao local fora dos testes

## Configuracao

A aplicacao usa variaveis de ambiente com valores padrão:

- `JWT_SECRET` - segredo do JWT
- `DB_URL` - URL do banco
- `DB_USER` - usuario do banco
- `DB_PASSWORD` - senha do banco
- `PORT` - porta da aplicacao
- `LOG_LEVEL` - nivel de log

Exemplo de configuracao local:

```env
JWT_SECRET=mySecretKey
DB_URL=jdbc:postgresql://localhost:5432/books_db
DB_USER=dev
DB_PASSWORD=dev
PORT=8080
LOG_LEVEL=INFO
```

## Como executar

### Windows

```bash
mvnw.cmd spring-boot:run
```

### Linux/macOS

```bash
./mvnw spring-boot:run
```

A aplicacao sobe por padrao na porta `8080`.

## Observabilidade com Grafana

Ao subir o `docker-compose`, o Grafana fica disponível em `http://localhost:3000` com o usuário `admin` e senha `admin`.

O stack já vem com:

- datasource do Prometheus provisionado automaticamente
- dashboard da aplicação Books API provisionado automaticamente

O Prometheus coleta as métricas expostas em `/actuator/prometheus`.

## Documentacao da API

- OpenAPI JSON: `/api-docs`
- Scalar UI: `/docs`

## Testes

Os testes usam PostgreSQL via Testcontainers com Docker e o profile `test`.

Executar a suite completa:

### Windows

```bash
mvnw.cmd test
```

### Linux/macOS

```bash
./mvnw test
```