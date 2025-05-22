# Checkpoint 2 - API de Gerenciamento de Clínicas

API RESTful para gerenciar pacientes e profissionais de saúde.

## Tecnologias

* Java
* Spring Boot
* Spring Web
* Spring Data JPA
* Spring DevTools
* SpringDoc (Swagger)
* Maven

## Pré-requisitos

* JDK (17/18)
* Maven (3.x)
* Banco de dados relacional (configurar em `application.properties`/`application.yml`)

## Como Executar

1.  Clone o projeto.
2.  Execute: `./mvnw spring-boot:run` (ou `mvnw.cmd spring-boot:run` no Windows).
3.  Acesse a API em `http://localhost:8080`.

## Endpoints

### Pacientes

* `POST /pacientes`: Criar paciente.
* `GET /pacientes`: Listar pacientes.
* `GET /pacientes/{id}`: Buscar paciente por ID.
* `PUT /pacientes/{id}`: Atualizar paciente.
* `DELETE /pacientes/{id}`: Deletar paciente.

### Profissionais

* `POST /profissionais`: Criar profissional.
* `GET /profissionais`: Listar profissionais.
* `GET /profissionais/{id}`: Buscar profissional por ID.
* `PUT /profissionais/{id}`: Atualizar profissional.
* `DELETE /profissionais/{id}`: Deletar profissional.

## Documentação

Acesse a documentação completa em `http://localhost:8080/swagger-ui/index.html`.

## Modelagem (Resumo)

* **Paciente:** `id`, `nome`, `endereco`, `bairro`, `email`, `telefone_completo`, `dataNascimento`, `createdAt`, `updatedAt`.
* **Profissional:** `id`, `nome`, `especialidade`, `valorHora`, `createdAt`, `updatedAt`.
* **Consulta:** `id`, `profissional` (FK), `paciente` (FK), `data`, `status`, `quantidadeHoras`, `valorConsulta`, `createdAt`, `updatedAt`.

## Wesley Assis 552516
## Guilherme Silva 98298