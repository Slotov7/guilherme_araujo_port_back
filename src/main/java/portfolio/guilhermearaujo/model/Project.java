package portfolio.guilhermearaujo.model;

import jakarta.persistence.*;
import lombok.Data;

// Indica que esta classe é uma entidade do JPA e será mapeada para uma tabela no banco de dados
@Entity
// Gera automaticamente getters, setters, equals, hashCode e toString usando Lombok
@Data
public class Project {
    // Define o atributo como chave primária
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Atributos do projeto
    private String name; // Nome do projeto
    private String description; // Descrição do projeto
    private String imageUrl; // URL da imagem do projeto
    private String repoUrl; // URL do repositório do projeto
}
