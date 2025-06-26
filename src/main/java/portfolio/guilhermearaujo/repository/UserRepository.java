package portfolio.guilhermearaujo.repository;

import portfolio.guilhermearaujo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

// Interface que estende JpaRepository para realizar operações de persistência na entidade User
public interface UserRepository extends JpaRepository<User, Long> {
    // Método que busca um usuário pelo nome de usuário
    // Retorna um Optional<User> para tratar o caso em que o usuário não é encontrado
    Optional<User> findByUsername(String username);
}