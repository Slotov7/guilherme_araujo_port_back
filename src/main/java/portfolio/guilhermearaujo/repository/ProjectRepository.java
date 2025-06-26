package portfolio.guilhermearaujo.repository;

import portfolio.guilhermearaujo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

// Define a interface ProjectRepository que herda de JpaRepository, permitindo operações de persistência
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
