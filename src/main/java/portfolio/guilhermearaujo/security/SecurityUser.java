package portfolio.guilhermearaujo.security;

import portfolio.guilhermearaujo.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

// Classe que implementa UserDetails do Spring Security para encapsular os dados do usuário
public class SecurityUser implements UserDetails {

    private final User user; // Instância de User que contém as informações do usuário do sistema

    // Construtor que recebe um objeto User e o atribui à variável interna
    public SecurityUser(User user) {
        this.user = user;
    }

    // Método que converte as roles do usuário em uma coleção de GrantedAuthority
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(user.getRoles().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Retorna a senha do usuário
    @Override
    public String getPassword() { return user.getPassword(); }

    // Retorna o nome de usuário
    @Override
    public String getUsername() { return user.getUsername(); }

    // Indica que a conta não expirou
    @Override
    public boolean isAccountNonExpired() { return true; }

    // Indica que a conta não está bloqueada
    @Override
    public boolean isAccountNonLocked() { return true; }

    // Indica que as credenciais não expiraram
    @Override
    public boolean isCredentialsNonExpired() { return true; }

    // Indica que a conta está habilitada
    @Override
    public boolean isEnabled() { return true; }
}