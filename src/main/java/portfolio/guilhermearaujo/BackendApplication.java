package portfolio.guilhermearaujo;

import org.springframework.beans.factory.annotation.Value; // Importante adicionar isto
import org.springframework.web.client.RestTemplate;
import portfolio.guilhermearaujo.model.Project;
import portfolio.guilhermearaujo.model.User;
import portfolio.guilhermearaujo.repository.ProjectRepository;
import portfolio.guilhermearaujo.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BackendApplication {

	// Injetamos os valores do application.properties aqui
	@Value("${setup.admin.username}")
	private String adminUsername;

	@Value("${setup.admin.password}")
	private String adminPassword;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(ProjectRepository projectRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			if (userRepository.findByUsername(adminUsername).isEmpty()) {

				User adminUser = new User();
				adminUser.setUsername(adminUsername);
				adminUser.setPassword(passwordEncoder.encode(adminPassword));
				adminUser.setRoles("ROLE_ADMIN");

				userRepository.save(adminUser);
				System.out.println(">>> [BackendApplication] Usuário ADMIN (" + adminUsername + ") criado com sucesso!");
			} else {
				System.out.println(">>> [BackendApplication] Usuário ADMIN já existe.");
			}
		};
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}