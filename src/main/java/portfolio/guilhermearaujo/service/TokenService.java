package portfolio.guilhermearaujo.service;

import portfolio.guilhermearaujo.security.SecurityUser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

// Indica que a classe é um serviço gerenciado pelo Spring
@Service
public class TokenService {

    @Value("${api.jwt.secret}") // Injeta o valor da propriedade definida no arquivo de configuração
    private String jwtSecretString;

    private Key signingKey; // Chave usada para assinar os tokens JWT

    @PostConstruct // Método executado após a construção do bean
    public void init() {
        // Converte a string secreta em uma chave para assinatura HMAC
        this.signingKey = Keys.hmacShaKeyFor(jwtSecretString.getBytes());
    }

    // Gera um token JWT para o usuário autenticado
    public String generateToken(Authentication authentication) {
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        Date now = new Date();// Data atual
        // Define a data de expiração para 10 horas a partir do agora
        Date expirationDate = new Date(now.getTime() + (10 * 60 * 60 * 1000)); // 10 horas

        // Cria e retorna o token JWT com as informações definidas
        return Jwts.builder()
                .setIssuer("Portfolio API") // Define o emissor do token
                .setSubject(principal.getUsername()) // Define o assunto/identificador do token (username)
                .setIssuedAt(now) // Data de emissão
                .setExpiration(expirationDate) // Data de expiração
                .signWith(signingKey, SignatureAlgorithm.HS256) // Assina o token com a chave e algoritmo HS256
                .compact();
    }

    // Valida se o token JWT é válido
    public boolean validateToken(String token) {
        try {
            // Tenta fazer o parse do token com a chave de assinatura
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true; // Token válido
        } catch (Exception e) {
            return false; // Token inválido ou expirado
        }
    }

    // Extrai o username do token JWT
    public String getUsernameFromToken(String token) {
        // Faz o parse do token e retorna o subject, que contém o username
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody().getSubject();
    }
}