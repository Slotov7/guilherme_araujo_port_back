package portfolio.guilhermearaujo.dto;

import lombok.Data;

@Data
public class LoginRequest{
    private String username;
    private  String password;

    private String recaptchaResponse; // Resposta do reCAPTCHA para validação de spam
}
