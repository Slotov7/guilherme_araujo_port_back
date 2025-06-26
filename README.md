# 🎯 Backend do Portfólio Pessoal

Este repositório contém o código-fonte do backend da minha aplicação de portfólio pessoal. A API foi desenvolvida com foco em **robustez**, **segurança** e **escalabilidade**, servindo como base para exibição dos meus projetos e gerenciamento de conteúdo.

---

## 🚀 Tecnologias Utilizadas

* **Java 17** — Versão LTS da linguagem Java.
* **Spring Boot 3.3.1** — Framework principal da aplicação.
* **Spring Web** — Criação de APIs RESTful.
* **Spring Data JPA** — Persistência de dados com ORM.
* **Spring Security** — Autenticação e autorização.
* **PostgreSQL** — Banco de dados relacional.
* **JWT (JSON Web Token)** — Autenticação stateless e segura.
* **Maven** — Gerenciador de dependências e build.
* **Lombok** — Redução de código boilerplate.

---

## ✨ Funcionalidades

* API RESTful para gerenciamento de projetos.
* **Endpoint público** para listar projetos: `GET /api/projects`.
* **Endpoints protegidos** para criação, edição e remoção de projetos (CRUD).
* Sistema de autenticação com JWT.
* Autorização baseada em **Roles**, com acesso exclusivo para usuários com `ROLE_ADMIN`.
* Criação automática de um usuário administrador ao iniciar a aplicação (para testes).

---

## 🛠️ Instalação e Configuração

### Pré-requisitos

* Java 17 ou superior
* Maven
* PostgreSQL

### Passos para execução local:

1. **Clone o repositório:**

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```

2. **Configure o banco de dados:**

Edite o arquivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/nome_da_sua_db
spring.datasource.username=seu_usuario_postgres
spring.datasource.password=sua_senha_postgres
```

3. **Build e execução da aplicação:**

Usando o Maven Wrapper:

```bash
# No Windows
.\mvnw.cmd clean install

# No Linux/macOS
./mvnw clean install
```

Execute o JAR gerado:

```bash
java -jar target/nome-do-seu-jar-0.0.1-SNAPSHOT.jar
```

A API estará disponível em: `http://localhost:8080`.

---

## 🔑 Endpoints da API

### 🔐 Autenticação

| Método | URL               | Protegido | Descrição                                          |
| ------ | ----------------- | --------- | -------------------------------------------------- |
| POST   | `/api/auth/login` | ❌         | Autentica o usuário e retorna um token JWT válido. |

**Exemplo de corpo da requisição:**

```json
{
  "username": "admin",
  "password": "senha123"
}
```

---

### 📁 Projetos

| Método | URL                  | Protegido | Descrição                             |
| ------ | -------------------- | --------- | ------------------------------------- |
| GET    | `/api/projects`      | ❌         | Lista todos os projetos.              |
| POST   | `/api/projects`      | ✅ (ADMIN) | Cria um novo projeto.                 |
| PUT    | `/api/projects/{id}` | ✅ (ADMIN) | Atualiza um projeto existente por ID. |
| DELETE | `/api/projects/{id}` | ✅ (ADMIN) | Remove um projeto por ID.             |

---

## 👤 Usuário Padrão

Ao iniciar a aplicação pela primeira vez, um usuário administrador é criado automaticamente com as credenciais:

* **Username:** `admin`
* **Senha:** `senha123`

---

## 🔐 Acessando Endpoints Protegidos

1. Realize login em `/api/auth/login` com as credenciais do administrador.
2. Copie o token JWT retornado.
3. Para acessar endpoints protegidos, inclua o seguinte header nas requisições:

```
Authorization: Bearer seu_token_jwt_aqui
```
