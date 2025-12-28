package portfolio.guilhermearaujo.controller;

import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import portfolio.guilhermearaujo.model.Technology;
import portfolio.guilhermearaujo.repository.TechnologyRepository;

import java.util.List;

@RestController
@RequestMapping("/api/technologies")
public class TechnologyController {

    private final TechnologyRepository technologyRepository;

    public TechnologyController(TechnologyRepository technologyRepository) {
        this.technologyRepository = technologyRepository;
    }

    @GetMapping
    public List<Technology> searchTechnologies(@RequestParam(defaultValue = "") String query) {
        if (query.trim().isEmpty()) {
            return List.of();
        }

        return technologyRepository.findByNameContainingIgnoreCase(query, PageRequest.of(0, 10));
    }
}