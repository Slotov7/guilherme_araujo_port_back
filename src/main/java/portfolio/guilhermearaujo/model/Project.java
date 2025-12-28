package portfolio.guilhermearaujo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 1000)
    private String imageUrl;

    @Column(nullable = true)
    private String repoUrl;

    @Column(length = 1000)
    private String technologies;
}