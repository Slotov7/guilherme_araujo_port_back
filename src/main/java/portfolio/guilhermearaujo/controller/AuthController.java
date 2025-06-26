package portfolio.guilhermearaujo.controller;

import portfolio.guilhermearaujo.dto.LoginRequest;
import portfolio.guilhermearaujo.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

// Declaração de um controlador REST que gerencia endpoints na API
@RestController
// Define que todos os endpoints deste controlador terão como base a rota "/api/auth"
@RequestMapping("/api/auth")
public class AuthController {

    // Gerenciador responsável por realizar a autenticação dos usuários
    private final AuthenticationManager authenticationManager;
    // Serviço responsável por gerar tokens JWT após a autenticação
    private final TokenService tokenService;

    // Construtor que injeta as dependências de AuthenticationManager e TokenService
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    // Endpoint para realizar o login e geração do token JWT
    // Anotação que mapeia requisições HTTP POST para "/api/auth/login"
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        // Cria um objeto com as credenciais do usuário obtidas do corpo da requisição
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        // Realiza a autenticação do usuário
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        // Se a autenticação for bem-sucedida, gera e retorna o token JWT
        return tokenService.generateToken(authentication);
    }
}