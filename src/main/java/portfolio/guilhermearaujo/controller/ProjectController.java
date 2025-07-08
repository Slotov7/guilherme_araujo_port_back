package portfolio.guilhermearaujo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import portfolio.guilhermearaujo.model.Project;
import portfolio.guilhermearaujo.repository.ProjectRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;
import portfolio.guilhermearaujo.service.ImageUploadService;

// Declara que essa classe é um controlador REST
@RestController
// Define a rota base para os endpoints deste controlador
@RequestMapping("/api/projects")
public class ProjectController {

    // Repositório para operações de persistência dos projetos
    private final ProjectRepository projectRepository;
    private final ImageUploadService imageUploadService;
    private final ObjectMapper objectMapper;

    // Construtor que injeta o repositório de projetos
    public ProjectController(ProjectRepository projectRepository, ImageUploadService imageUploadService, ObjectMapper objectMapper) {
        this.projectRepository = projectRepository;
        this.imageUploadService = imageUploadService;
        this.objectMapper = objectMapper;
    }
    // Endpoint para obter todos os projetos
    @GetMapping
    public List<Project> getProjects() {
        // Retorna a lista com todos os projetos disponíveis
        return projectRepository.findAll();
    }

    // Endpoint para criar um novo projeto
    // Restringe o acesso a usuários com o papel ADMIN
    @PostMapping(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> createProject(@RequestPart("project") String projectJson, // Recebe os dados do projeto como uma string JSON
                                                 @RequestPart("image") MultipartFile imageFile) {

       try {
           Project project = objectMapper.readValue(projectJson, Project.class);

           String imageUrl = imageUploadService.uploadImage(imageFile);
           project.setImageUrl(imageUrl);

           project.setId(null);
           Project savedProject = projectRepository.save(project);
           return new ResponseEntity<>(savedProject, HttpStatus.CREATED);
       }catch (IOException exception){
           exception.printStackTrace(); // Logar o erro é uma boa prática
           return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    // Endpoint para atualizar um projeto existente
    // Restringe o acesso a usuários com o papel ADMIN
    @PutMapping(value ="/{id}", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Project> updateProject(@PathVariable Long id,
                                                 @RequestPart("project") String projectJson,
                                                 @RequestPart(value = "image", required = false) MultipartFile imageFile) {
        try {
            Project updatedDetails = objectMapper.readValue(projectJson, Project.class);
            return projectRepository.findById(id)
                    .map(project -> {
                        // Atualiza os dados de texto
                        project.setName(updatedDetails.getName());
                        project.setDescription(updatedDetails.getDescription());
                        project.setRepoUrl(updatedDetails.getRepoUrl());

                        // Se um novo ficheiro de imagem foi enviado, faz o upload e atualiza a URL
                        if (imageFile != null && !imageFile.isEmpty()) {
                            try {
                                String newImageUrl = imageUploadService.uploadImage(imageFile);
                                project.setImageUrl(newImageUrl);
                            } catch (IOException e) {
                                // Lançar uma exceção personalizada aqui seria o ideal
                                throw new RuntimeException("Falha ao fazer upload da nova imagem.", e);
                            }
                        }

                        Project saved = projectRepository.save(project);
                        return ResponseEntity.ok(saved);
                    })
                    .orElse(ResponseEntity.notFound().build());

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST); // Erro na conversão do JSON
        } catch (RuntimeException e) {
            // Captura a exceção do upload de imagem
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        return projectRepository.findById(id)
                .map(ResponseEntity::ok) // Forma curta de .map(project -> ResponseEntity.ok(project))
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