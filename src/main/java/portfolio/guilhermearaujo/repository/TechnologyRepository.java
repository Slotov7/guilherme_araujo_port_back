package portfolio.guilhermearaujo.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import portfolio.guilhermearaujo.model.Technology;

import java.util.List;

public interface TechnologyRepository extends JpaRepository<Technology, Long> {
    List<Technology> findByNameContainingIgnoreCase(String name, Pageable pageable);
}