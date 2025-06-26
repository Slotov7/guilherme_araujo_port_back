package portfolio.guilhermearaujo.controller;

import portfolio.guilhermearaujo.model.Project;
import portfolio.guilhermearaujo.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectRepository projectRepository;

    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @GetMapping
    public List<Project> getProjects() {
        return projectRepository.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> createProject(@RequestBody Project newProject) {
        newProject.setId(null);
        Project savedProject = projectRepository.save(newProject);
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project updatedProjectDetails) {
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(updatedProjectDetails.getName());
                    project.setDescription(updatedProjectDetails.getDescription());
                    project.setImageUrl(updatedProjectDetails.getImageUrl());
                    project.setRepoUrl(updatedProjectDetails.getRepoUrl());
                    Project saved = projectRepository.save(project);
                    return ResponseEntity.ok(saved);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (!projectRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        projectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}