package portfolio.guilhermearaujo.config;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import portfolio.guilhermearaujo.security.JwtAuthFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Declara que esta classe define beans para o contexto do Spring
@EnableWebSecurity // Habilita a segurança na camada web do Spring Security
@EnableMethodSecurity // Permite utilizar anotações de segurança em métodos (ex.: @PreAuthorize)
public class SecurityConfig {

    // Filtro responsável por validar o token JWT em cada requisição
    private final JwtAuthFilter jwtAuthFilter;
    private final UserDetailsService userDetailsService;

    // Construtor que injeta o filtro JWT na classe de configuração
    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userDetailsService) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
    }

    // Define um bean para o encoder de senhas utilizando o algoritmo BCrypt
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        // Define o serviço que busca os usuários
        authProvider.setUserDetailsService(userDetailsService);
        // Define o codificador de senhas que será usado para a verificação
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    // Cria um bean para o gerenciador de autenticação, necessário para processar a autenticação de usuários
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // Configura a cadeia de filtros de segurança da aplicação
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                // Desabilita o CSRF, pois a API é stateless
                .csrf(AbstractHttpConfigurer::disable)
                // Configura a sessão para não manter estado (stateless)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                // Define as regras de autorização para os endpoints
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/contact").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/projects").permitAll()
                        .anyRequest().authenticated()
                )
                // Adiciona o filtro JWT antes do filtro padrão de autenticação
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Finaliza a configuração e retorna o SecurityFilterChain
                .build();
    }
}