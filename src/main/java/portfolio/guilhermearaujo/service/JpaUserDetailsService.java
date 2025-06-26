package portfolio.guilhermearaujo.service;

import portfolio.guilhermearaujo.repository.UserRepository;
import portfolio.guilhermearaujo.security.SecurityUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Indica que a classe é um componente de serviço gerenciado pelo Spring
@Service
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository; // Repositório para acesso aos dados dos usuários

    // Construtor com injeção de dependência do UserRepository
    public JpaUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Implementa o método para carregar os detalhes do usuário pelo nome
    // Se o usuário não for encontrado, lança UsernameNotFoundException
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(SecurityUser::new) // Converte o objeto User em SecurityUser para uso com Spring Security
                .orElseThrow(() -> new UsernameNotFoundException("Username not found: " + username));
    }
}