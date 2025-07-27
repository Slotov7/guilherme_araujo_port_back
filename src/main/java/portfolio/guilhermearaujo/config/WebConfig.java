package portfolio.guilhermearaujo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements  WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 1. Aplica esta configuração a TODOS os endpoints da nossa API
                .allowedOriginPatterns("*") // aceita coringas e protocolos diferentes
                .allowedMethods("*") // 3. Permite os métodos HTTP que usamos
                .allowedHeaders("*") // 4. Permite todos os cabeçalhos nos pedidos
                .allowCredentials(true); // 5. Permite o envio de credenciais (como cookies ou tokens de autorização)
    }
}
