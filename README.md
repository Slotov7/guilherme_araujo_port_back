# 💼 API do Portfólio Pessoal | Backend com Spring Boot

![Java](https://img.shields.io/badge/Java-17-blue?style=for-the-badge\&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.3-green?style=for-the-badge\&logo=spring)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue?style=for-the-badge\&logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-Auth-purple?style=for-the-badge\&logo=jsonwebtokens)

Este repositório contém o backend da minha aplicação de portfólio pessoal, desenvolvido com **Java 17** e **Spring Boot 3.3**.
A API foi projetada para ser **robusta, segura e escalável**, permitindo o gerenciamento de projetos e a interação com visitantes via formulário de contato.

---

## ✨ Funcionalidades

* **API RESTful completa:** CRUD para gerenciamento de projetos.
* **Autenticação e autorização seguras:** Implementadas com **Spring Security**.
* **JWT Stateless Auth:** Autenticação via token JWT, sem uso de sessões.
* **Controle de acesso por roles:** Apenas usuários com `ROLE_ADMIN` podem acessar endpoints de administração, usando `@PreAuthorize`.
* **Envio de e-mails transacionais:** Endpoint público para formulário de contato com envio via **SendGrid**.
* **Proteção contra bots:** Integração com **Google reCAPTCHA v3** para login e contato.
* **Documentação interativa:** Endpoints testáveis via **Swagger UI (OpenAPI)**.
* **Configuração flexível por ambiente:** Feature flags no `application.properties` para ativar/desativar recursos como o reCAPTCHA.

---

## 🛠️ Tecnologias Utilizadas

| Categoria                 | Tecnologias/Ferramentas                            |
| ------------------------- | -------------------------------------------------- |
| **Linguagem & Framework** | Java 17, Spring Boot 3.3                           |
| **Banco de Dados**        | PostgreSQL, Spring Data JPA, Hibernate             |
| **Segurança**             | Spring Security, JWT (jjwt), BCrypt                |
| **API Web**               | Spring Web (MVC), RESTful                          |
| **Serviços Externos**     | SendGrid (e-mail), Google reCAPTCHA v3 (anti-spam) |
| **Documentação**          | Swagger (OpenAPI 3) com `springdoc-openapi`        |
| **Build & Gerenciamento** | Maven                                              |
| **Ferramentas de Dev**    | IntelliJ, Postman, Lombok, Spring DevTools         |

---

## 🚀 Como Executar Localmente

### Pré-requisitos

* JDK 17+
* Maven 3.8+
* PostgreSQL instalado e rodando
* Banco de dados criado (ex: `portfolio_db`)

### Passo a passo

1. **Clone o repositório:**

```bash
git clone https://github.com/Slotov7/guilherme_araujo_port_back.git
cd guilherme_araujo_port_back
```

2. **Configure o ambiente:**

Crie o arquivo `application.properties` em `src/main/resources/`, com base no modelo `application.properties.example`. Preencha com suas credenciais e chaves (DB, JWT, SendGrid, reCAPTCHA).

3. **Compile e execute:**

```bash
# Compilar
./mvnw clean install

# Executar o JAR
java -jar target/guilhermearaujo-0.0.1-SNAPSHOT.jar
```

A API estará disponível em:
📍 `http://localhost:8080`
📄 Documentação Swagger: `http://localhost:8080/swagger-ui.html`

---

## 🔐 Autenticação e Teste de Endpoints

### Usuário padrão inicial

Ao rodar pela primeira vez, um usuário admin é criado automaticamente:

* **Username:** `admin`
* **Password:** `senha123`

### Como usar o JWT:

1. Faça um `POST` em `/api/auth/login` com suas credenciais.
2. Copie o token retornado.
3. No Swagger UI, clique em "Authorize" e cole o token no formato:
   `Bearer <seu_token>`
4. Pronto! Você pode testar endpoints protegidos.

---

## 📂 Endpoints

A documentação completa dos endpoints pode ser acessada no **Swagger UI**. Alguns exemplos disponíveis:

* `/api/projects` – CRUD de projetos (restrito a admins)
* `/api/auth/login` – Geração de JWT
* `/api/contact` – Envio de mensagem para o administrador
* `/api/public/**` – Endpoints abertos

---

## 💡 Notas Finais

Este projeto foi uma excelente oportunidade para aprofundar conhecimentos em segurança, arquitetura de aplicações e boas práticas com Spring Boot.
O código está disponível para estudos, sugestões e contribuições. Sinta-se à vontade para explorar!

---

Desenvolvido por **Guilherme Araújo**
