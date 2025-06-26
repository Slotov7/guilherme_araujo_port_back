package portfolio.guilhermearaujo.controller;

import portfolio.guilhermearaujo.model.Project;
import portfolio.guilhermearaujo.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

// Declara que essa classe é um controlador REST
@RestController
// Define a rota base para os endpoints deste controlador
@RequestMapping("/api/projects")
public class ProjectController {

    // Repositório para operações de persistência dos projetos
    private final ProjectRepository projectRepository;

    // Construtor que injeta o repositório de projetos
    public ProjectController(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // Endpoint para obter todos os projetos
    @GetMapping
    public List<Project> getProjects() {
        // Retorna a lista com todos os projetos disponíveis
        return projectRepository.findAll();
    }

    // Endpoint para criar um novo projeto
    // Restringe o acesso a usuários com o papel ADMIN
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> createProject(@RequestBody Project newProject) {
        // Garante que o ID é nulo para que seja gerado automaticamente
        newProject.setId(null);
        // Salva o novo projeto no banco de dados
        Project savedProject = projectRepository.save(newProject);
        // Retorna o projeto salvo com o status HTTP CREATED (201)
        return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
    }

    // Endpoint para atualizar um projeto existente
    // Restringe o acesso a usuários com o papel ADMIN
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project updatedProjectDetails) {
        // Procura o projeto pelo ID e, se encontrado, atualiza seus detalhes
        return projectRepository.findById(id)
                .map(project -> {
                    project.setName(updatedProjectDetails.getName());
                    project.setDescription(updatedProjectDetails.getDescription());
                    project.setImageUrl(updatedProjectDetails.getImageUrl());
                    project.setRepoUrl(updatedProjectDetails.getRepoUrl());
                    Project saved = projectRepository.save(project);
                    // Retorna o projeto atualizado com status HTTP OK (200)
                    return ResponseEntity.ok(saved);
                })
                // Se o projeto não for encontrado, retorna status HTTP NOT FOUND (404)
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para deletar um projeto existente
    // Restringe o acesso a usuários com o papel ADMIN
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        // Verifica se o projeto existe
        if (!projectRepository.existsById(id)) {
            // Retorna status NOT FOUND (404) se o projeto não for encontrado
            return ResponseEntity.notFound().build();
        }
        // Deleta o projeto e retorna status HTTP NO CONTENT (204)
        projectRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}