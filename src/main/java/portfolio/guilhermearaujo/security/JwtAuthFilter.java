package portfolio.guilhermearaujo.security;

import portfolio.guilhermearaujo.service.JpaUserDetailsService;
import portfolio.guilhermearaujo.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

// Indica ao Spring que esta classe deve ser gerenciada como um componente
@Component
public class JwtAuthFilter extends OncePerRequestFilter { // Filtro que é executado uma única vez por requisição

    private final TokenService tokenService; // Serviço para manipulação e validação do token JWT
    private final JpaUserDetailsService userDetailsService; // Serviço que carrega os detalhes do usuário

    // Construtor que injeta as dependências necessárias
    public JwtAuthFilter(TokenService tokenService, JpaUserDetailsService userDetailsService) {
        this.tokenService = tokenService;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        // Extrai o token JWT do cabeçalho da requisição
        String token = extractTokenFromRequest(request);
        // Se o token existir e for válido, realiza a autenticação do usuário
        if (token != null && tokenService.validateToken(token)) {
            // Recupera o username contido no token
            String username = tokenService.getUsernameFromToken(token);
            // Carrega os detalhes do usuário a partir do username
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            // Cria um objeto de autenticação com as informações do usuário e define no contexto de segurança
            var authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        // Dá continuidade à cadeia de filtros
        filterChain.doFilter(request, response);
    }

    // Método auxiliar que extrai o token do cabeçalho "Authorization" da requisição
    private String extractTokenFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        // Verifica se o token existe e se inicia com "Bearer ", removendo o prefixo se existir
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}