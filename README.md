# Parking-RestAPI-JWT 🚗🔐

Uma API REST robusta para gerenciamento de estacionamentos, com autenticação JWT, controle de acesso baseado em roles e documentação Swagger. Desenvolvida para demonstrar habilidades em Spring Boot, segurança e arquitetura de APIs.

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.5-brightgreen)
![JWT](https://img.shields.io/badge/JWT-Auth-orange)

## Tecnologias Utilizadas 🛠️
- **Java 17**
- **Spring Boot 3**
- **Spring Security**
- **JWT (JSON Web Token)**
- **Spring Data JPA**
- **H2 Database** (Banco em memória)
- **ModelMapper** (Para DTOs)
- **Swagger OpenAPI 3** (Documentação)
- **Lombok**
- **Java Validation**
- **Maven/Gradle**

## Funcionalidades Principais 💡
- ✅ Autenticação JWT com roles (`ADMIN` e `CLIENTE`)
- ✅ CRUD completo para:
  - Usuários
  - Clientes
  - Vagas de estacionamento
  - Registros de ocupação (ClienteVaga)
- ✅ Controle de acesso granular por endpoints
- ✅ Validação de dados com mensagens internacionalizáveis
- ✅ Documentação interativa via Swagger UI
- ✅ Tratamento customizado de exceções
- ✅ Auditoria de entidades (data de criação/modificação)

## Estrutura do Projeto 📂

### Documentação Swagger:
- Swagger UI: [`http://localhost:8080/docs-park.html`](http://localhost:8080/docs-park.html)
- OpenAPI 3.1: [`http://localhost:8080/v3/api-docs`](http://localhost:8080/v3/api-docs)


## Como Executar 🚀
Pré-requisitos: Java 17+, Maven/Gradle

```bash
git clone https://github.com/Jorge-Matheus/Parking-RestAPI-JWT.git
cd Parking-RestAPI-JWT
mvn spring-boot:run
