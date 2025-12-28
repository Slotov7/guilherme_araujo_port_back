package portfolio.guilhermearaujo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.guilhermearaujo.model.Technology;
import java.util.Optional;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    Optional<Technology> findByName(String name);
}