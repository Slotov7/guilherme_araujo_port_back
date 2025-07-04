package portfolio.guilhermearaujo.dto;

import lombok.Data;

@Data
public class ContactRequest{
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private String message;

    private String recaptchaResponse; // Resposta do reCAPTCHA para validação de spamq
}
