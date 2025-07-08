package portfolio.guilhermearaujo.config;

import org.springframework.context.annotation.Configuration;
import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;


@Configuration
public class CloudinaryConfig {

    @Value("${cloudinary.url}")
    private String cloudinaryUrl;

    @Bean
    public Cloudinary cloudinary() {
        // 2. O construtor do Cloudinary aceita a URL diretamente.
        return new Cloudinary(cloudinaryUrl);
    }

}
