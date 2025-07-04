package portfolio.guilhermearaujo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import portfolio.guilhermearaujo.dto.LoginRequest;
import portfolio.guilhermearaujo.service.RecaptchaService;
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

    private final RecaptchaService recaptchaService;
    private final ObjectMapper objectMapper;

    // Construtor que injeta as dependências de AuthenticationManager e TokenService
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, RecaptchaService recaptchaService, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.recaptchaService = recaptchaService;
        this.objectMapper = objectMapper;
    }

    @Value("${recaptcha.validation.enabled}")
    private boolean recaptchaEnabled;

    // Endpoint para realizar o login e geração do token JWT
    // Anotação que mapeia requisições HTTP POST para "/api/auth/login"
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody String rawJsonBody) {
        try {
            LoginRequest loginRequest = objectMapper.readValue(rawJsonBody, LoginRequest.class);

            boolean isRecaptchaValid = recaptchaService.validateRecaptcha(loginRequest.getRecaptchaResponse());
                if (recaptchaEnabled){
                    if (!isRecaptchaValid) {
                        System.out.println(">>> [AuthController] Validação do reCAPTCHA falhou. Pedido bloqueado.");
                        return ResponseEntity.badRequest().body("ReCAPTCHA inválido");
                    }
                }
            System.out.println(">>> [AuthController] Validação do reCAPTCHA bem-sucedida. Tentando autenticar utilizador...");
            var usernamePassword = new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            Authentication authentication = authenticationManager.authenticate(usernamePassword);

            String jwtToken = tokenService.generateToken(authentication);

            return ResponseEntity.ok(jwtToken);


        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Falha na autenticação.");
        }
    }
}