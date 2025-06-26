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

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ProjectRepository projectRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			// Limpa e popula os projetos
			projectRepository.deleteAll();
			Project p1 = new Project();
			p1.setName("Meu Portfólio - Backend");
			p1.setDescription("API REST com Spring Boot para o meu portfólio pessoal.");
			p1.setImageUrl("https://via.placeholder.com/300");
			p1.setRepoUrl("https://github.com/");
			projectRepository.save(p1);

			// Limpa e popula o utilizador administrador
			userRepository.deleteAll();
			User adminUser = new User();
			adminUser.setUsername("admin");
			adminUser.setPassword(passwordEncoder.encode("senha123"));
			adminUser.setRoles("ROLE_ADMIN");
			userRepository.save(adminUser);
		};
	}
}