# Sistema de Gestão Condominial

[![CI](https://github.com/jouberloliveira/condominio-gestao/actions/workflows/ci.yml/badge.svg)](https://github.com/jouberloliveira/condominio-gestao/actions/workflows/ci.yml)

Sistema web para gestão de condomínios, desenvolvido com Java 21 + Spring Boot 3 + Thymeleaf + Tailwind CSS.

## Funcionalidades

- **Unidades** — Cadastro de unidades com bloco, número, andar, vaga e situação (OCUPADA / DESOCUPADA / EM_REFORMA)
- **Moradores** — Cadastro de moradores vinculados a unidades, com CPF único e tipo (PROPRIETÁRIO / INQUILINO / DEPENDENTE)
- **Visitantes** — Controle de entrada com tipo (VISITA / PRESTADOR / ENTREGADOR) e autorização por morador
- **Reservas** — Reserva de áreas comuns com status e controle de conflitos de horário
- **Ocorrências** — Registro de ocorrências com tipo, prioridade e status

## Stack

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.x |
| Persistência | Spring Data JPA + H2 (in-memory) |
| Templates | Thymeleaf |
| CSS | Tailwind CSS via CDN |
| Build | Maven |
| Validação | Jakarta Bean Validation + Lombok |

## Como executar

### Pré-requisitos

- Java 21+
- Maven 3.8+

### Rodar localmente

```bash
./mvnw spring-boot:run
```

Acesse: [http://localhost:8080](http://localhost:8080)

### Console H2

Disponível em [http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- JDBC URL: `jdbc:h2:mem:condominios`
- User: `sa`
- Password: _(em branco)_

## Estrutura do projeto

```
src/main/java/com/condominio/gestao/
├── controller/      # Controllers MVC (CRUD de cada entidade)
├── enums/           # Enums de domínio
├── exception/       # Exceções de negócio
├── model/           # Entidades JPA
├── repository/      # Interfaces Spring Data JPA
└── service/         # Lógica de negócio e regras
src/main/resources/
├── templates/       # Templates Thymeleaf por entidade
└── application.properties
```

## Licença

Projeto educacional / interno. Sem licença de distribuição.
