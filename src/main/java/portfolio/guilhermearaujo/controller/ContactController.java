package portfolio.guilhermearaujo.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import portfolio.guilhermearaujo.dto.ContactRequest;
import portfolio.guilhermearaujo.service.EmailService;
import portfolio.guilhermearaujo.service.RecaptchaService;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final EmailService emailService;
    private final RecaptchaService recaptchaService;
    private final ObjectMapper objectMapper;

    public ContactController(EmailService emailService, RecaptchaService recaptchaService, ObjectMapper objectMapper) {
        this.emailService = emailService;
        this.recaptchaService = recaptchaService;
        this.objectMapper = objectMapper;
    }

    @Value("${recaptcha.validation.enabled}")
    private boolean recaptchaEnabled;

    @PostMapping
    public ResponseEntity<Void> sendEmail(@RequestBody ContactRequest contactRequest) {
        try {
            if (recaptchaEnabled) {
                String recaptchaToken = contactRequest.getRecaptchaResponse();
                boolean isRecaptchaValid = recaptchaService.validateRecaptcha(recaptchaToken);
                if (!isRecaptchaValid) {
                    System.out.println(">>> [ContactController] Validação do reCAPTCHA falhou. Pedido bloqueado.");
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
                }
            }
            System.out.println(">>> [ContactController] Validação do reCAPTCHA bem-sucedida. Enviando e-mail...");
            emailService.sendContactEmail(contactRequest);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
