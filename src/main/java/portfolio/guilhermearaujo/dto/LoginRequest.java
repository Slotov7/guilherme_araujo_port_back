package portfolio.guilhermearaujo.dto;

/**
 * Record que representa a requisição de login contendo as credenciais do usuário.
 * Esta classe é utilizada para encapsular o username e password durante o
 * processo de autenticação.
 *
 * Por ser um record, os métodos construtor, getters, equals, hashCode e toString
 * são gerados automaticamente, garantindo imutabilidade.
 */
public record LoginRequest(String username, String password) {
}
