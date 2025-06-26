package portfolio.guilhermearaujo.model;

import jakarta.persistence.*;
import lombok.Data;

import jakarta.persistence.*;
import lombok.Data;

// Indica que esta classe é uma entidade do JPA e será mapeada para uma tabela no banco de dados
@Entity
// Especifica o nome da tabela no banco de dados como "app_user"
@Table(name = "app_user")
// Gera automaticamente getters, setters, equals, hashCode e toString usando Lombok
@Data
public class User {
    // Define o atributo como chave primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Campo único que armazena o nome do usuário, não permitindo valores nulos
    @Column(unique = true, nullable = false)
    private String username;

    // Campo que armazena a senha do usuário, não permitindo valores nulos
    @Column(nullable = false)
    private String password;

    // Campo que armazena os papéis (roles) do usuário, não permitindo valores nulos
    @Column(nullable = false)
    private String roles;
}
