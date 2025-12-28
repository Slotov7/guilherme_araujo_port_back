package portfolio.guilhermearaujo.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "technologies")
public class Technology {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(length = 500)
    private String imageUrl;
}