package portfolio.guilhermearaujo.controller;

import portfolio.guilhermearaujo.dto.LoginRequest;
import portfolio.guilhermearaujo.service.TokenService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken(authentication);
    }
}