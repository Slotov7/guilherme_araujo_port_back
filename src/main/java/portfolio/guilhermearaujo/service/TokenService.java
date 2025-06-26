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

@Service
public class TokenService {

    @Value("${api.jwt.secret}")
    private String jwtSecretString;
    private Key signingKey;

    @PostConstruct
    public void init() {
        this.signingKey = Keys.hmacShaKeyFor(jwtSecretString.getBytes());
    }

    public String generateToken(Authentication authentication) {
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + (10 * 60 * 60 * 1000)); // 10 horas

        return Jwts.builder()
                .setIssuer("Portfolio API")
                .setSubject(principal.getUsername())
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token).getBody().getSubject();
    }
}