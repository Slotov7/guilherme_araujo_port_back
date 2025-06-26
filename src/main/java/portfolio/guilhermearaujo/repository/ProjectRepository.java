package portfolio.guilhermearaujo.repository;

import portfolio.guilhermearaujo.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
