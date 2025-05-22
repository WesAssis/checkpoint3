# Checkpoint 3 - API de Gerenciamento de Consultas Médicas

API RESTful para gerenciar pacientes, profissionais de saúde e consultas.

## Tecnologias
- Java 17/18
- Spring Boot 3.4.*
- Spring Web
- Spring Data JPA
- Spring DevTools
- SpringDoc (Swagger)
- Maven
- MySQL

## Pré-requisitos
- JDK (17/18)
- Maven (3.x)
- MySQL (configurar em application.properties)

## Como Executar
1. Clone o projeto.
2. Crie o banco de dados: `CREATE DATABASE checkpoint3;`
3. Configure o arquivo `application.properties`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/checkpoint3
   spring.datasource.username=seu_usuario
   spring.datasource.password=sua_senha
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
   ```
4. Execute: `./mvnw spring-boot:run` (ou `mvnw.cmd spring-boot:run` no Windows).
5. Acesse a API em http://localhost:8080.

## Endpoints

### Pacientes
- POST /pacientes: Criar paciente.
- GET /pacientes?sort={asc, desc}: Listar pacientes com ordenação.
- GET /pacientes/{id}: Buscar paciente por ID.
- PUT /pacientes/{id}: Atualizar paciente.
- DELETE /pacientes/{id}: Deletar paciente.
- GET /pacientes/{id}/consultas?status={AGENDADA, REALIZADA, CANCELADA}&data_de=24-04-2025&data_ate=25-04-2025: Consultas de um paciente com filtros.

### Profissionais
- POST /profissionais: Criar profissional.
- GET /profissionais?sort={asc, desc}: Listar profissionais com ordenação.
- GET /profissionais/{id}: Buscar profissional por ID.
- PUT /profissionais/{id}: Atualizar profissional.
- DELETE /profissionais/{id}: Deletar profissional.
- GET /profissionais/{id}/stats: Estatísticas do profissional.
- GET /profissionais/{id}/consultas?status={AGENDADA, REALIZADA, CANCELADA}&data_de=24-04-2025&data_ate=25-04-2025: Consultas de um profissional com filtros.

### Consultas
- POST /consultas: Criar consulta.
- GET /consultas: Listar consultas.
- GET /consultas/{id}: Buscar consulta por ID.
- PUT /consultas/{id}: Atualizar consulta.
- DELETE /consultas/{id}: Deletar consulta.
- GET /consultas?status={AGENDADA, REALIZADA, CANCELADA}&data_de=24-04-2025&data_ate=25-04-2025: Consultas com filtros.

## Documentação
Acesse a documentação completa em http://localhost:8080/swagger-ui/index.html.

## Modelagem (Resumo)
- **Paciente**: id, nome, endereco, bairro, email, telefone_completo, dataNascimento, createdAt, updatedAt.
- **Profissional**: id, nome, especialidade, valorHora, createdAt, updatedAt.
- **Consulta**: id, profissional (FK), paciente (FK), data, statusConsulta, quantidadeHoras, valorConsulta, createdAt, updatedAt.

## Solução de Problemas
Se encontrar o erro "Unknown database 'checkpoint3'", certifique-se de criar o banco de dados antes de iniciar a aplicação:
```sql
CREATE DATABASE checkpoint3;
```
