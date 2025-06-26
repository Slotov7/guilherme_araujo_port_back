package portfolio.guilhermearaujo;


import portfolio.guilhermearaujo.model.Project;
import portfolio.guilhermearaujo.model.User;
import portfolio.guilhermearaujo.repository.ProjectRepository;
import portfolio.guilhermearaujo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;

// Anota a classe como ponto de entrada da aplicação Spring Boot
@SpringBootApplication
public class BackendApplication {

	// Método principal que inicia a aplicação Spring Boot
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	// Cria um bean CommandLineRunner para executar o código após a inicialização da aplicação
	@Bean
	CommandLineRunner initDatabase(ProjectRepository projectRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Remove todos os registros antigos de projetos
			projectRepository.deleteAll();
			// Cria e configura um novo projeto para popular o banco de dados
			Project p1 = new Project();
			p1.setName("Meu Portfólio - Backend"); // Nome do projeto
			p1.setDescription("API REST com Spring Boot para o meu portfólio pessoal."); // Descrição do projeto
			p1.setImageUrl("https://via.placeholder.com/300"); // URL da imagem do projeto
			p1.setRepoUrl("https://github.com/"); // URL do repositório do projeto
			projectRepository.save(p1); // Salva o projeto no banco de dados

			// Remove todos os registros antigos de usuários
			userRepository.deleteAll();
			// Cria e configura um usuário administrador para o sistema
			User adminUser = new User();
			adminUser.setUsername("admin"); // Nome de usuário do administrador
			adminUser.setPassword(passwordEncoder.encode("senha123")); // Senha do administrador, codificada com PasswordEncoder
			adminUser.setRoles("ROLE_ADMIN"); // Define o papel do usuário como ADMIN
			userRepository.save(adminUser); // Salva o usuário administrador no banco de dados
		};
	}
}