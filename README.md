# Sistema de Gestão Condominial

Sistema web para gestão de condomínios, desenvolvido com Java 21 + Spring Boot 3 + Thymeleaf + Tailwind CSS.

## Funcionalidades

- **Unidades** — Cadastro de unidades com bloco, número, andar, vaga e situação (OCUPADA / DESOCUPADA / EM_REFORMA)
- **Moradores** — Cadastro de moradores vinculados a unidades, com CPF único e tipo (PROPRIETÁRIO / INQUILINO / DEPENDENTE)
- **Visitantes** — Controle de entrada com tipo (VISITA / PRESTADOR / ENTREGADOR) e autorização por morador
- **Reservas** — Reserva de áreas comuns com status e controle de conflitos de horário
- **Ocorrências** — Registro de ocorrências com tipo, prioridade e status

## Stack Tecnológica

| Camada | Tecnologia |
|---|---|
| Linguagem | Java 21 |
| Framework | Spring Boot 3.2.3 |
| Persistência | Spring Data JPA + H2 (in-memory) |
| Templates | Thymeleaf |
| CSS | Tailwind CSS via CDN |
| Build | Maven 3.8+ |
| Validação | Jakarta Bean Validation |
| Utilitários | Lombok 1.18.46 |

## Pré-requisitos

Antes de rodar o projeto localmente, certifique-se de ter instalado:

- **Java 21+** ([Download OpenJDK](https://adoptium.net/))
- **Maven 3.8+** ([Download Maven](https://maven.apache.org/download.cgi))

Para verificar se estão instalados corretamente:

```bash
java -version   # deve mostrar versão 21.x
mvn -version    # deve mostrar versão 3.8.x ou superior
```

## Instruções de Execução Local

### Modo desenvolvimento (H2 in-memory)

1. Clone o repositório:

```bash
git clone https://github.com/jouberloliveira/condominio-gestao.git
cd condominio-gestao
```

2. Execute o projeto:

```bash
mvn spring-boot:run
```

3. Acesse a aplicação em: **http://localhost:8080**

### Console H2 (banco de dados)

Durante o desenvolvimento, você pode acessar o console do H2 para inspecionar os dados:

**URL:** http://localhost:8080/h2-console

**Credenciais:**
- JDBC URL: `jdbc:h2:mem:condominios`
- User: `sa`
- Password: _(deixe em branco)_

### Executar testes

```bash
mvn test
```

## Estrutura do Projeto

```
src/main/java/com/condominio/gestao/
├── controller/      # Controllers MVC (CRUD de cada entidade)
├── enums/           # Enums de domínio (SituacaoUnidade, TipoMorador, etc.)
├── exception/       # Exceções de negócio
├── model/           # Entidades JPA (@Entity)
├── repository/      # Interfaces Spring Data JPA
└── service/         # Lógica de negócio e regras de validação

src/main/resources/
├── templates/       # Templates Thymeleaf organizados por entidade
│   ├── index.html
│   ├── layout.html
│   ├── moradores/
│   ├── ocorrencias/
│   ├── reservas/
│   ├── unidades/
│   └── visitantes/
└── application.properties  # Configurações da aplicação
```

## Observações

- **Banco de dados:** o projeto usa H2 in-memory por padrão. Os dados são recriados a cada reinício da aplicação (`spring.jpa.hibernate.ddl-auto=create-drop`).
- **DevTools:** Spring Boot DevTools está habilitado para hot-reload durante desenvolvimento.
- **Lombok:** as entidades usam Lombok para reduzir boilerplate. Certifique-se de que seu IDE tem o plugin instalado.

## Licença

Projeto educacional / interno. Sem licença de distribuição.
