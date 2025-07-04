package portfolio.guilhermearaujo.service;

import jakarta.mail.MessagingException;
import  jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import portfolio.guilhermearaujo.dto.ContactRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailService {
    private final JavaMailSender mailSender;

    @Value("${portfolio.mail.from}")
    private String fromEmail;

    @Value("${portfolio.mail.from}")
    private String toEmail;

    @Value("classpath:templates/email-template.html")
    private Resource emailTemplate;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendContactEmail(ContactRequest contactRequest) {
        try {

            String htmlBody = emailTemplate.getContentAsString(StandardCharsets.UTF_8);

            htmlBody = safeReplace(htmlBody, "{{senderFirstName}}", contactRequest.getFirstName());
            htmlBody = safeReplace(htmlBody, "{{senderLastName}}", contactRequest.getLastName());
            htmlBody = safeReplace(htmlBody, "{{senderPhone}}", contactRequest.getPhone());
            htmlBody = safeReplace(htmlBody, "{{senderEmail}}", contactRequest.getEmail());
            htmlBody = safeReplace(htmlBody, "{{message}}", contactRequest.getMessage());

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Nova Mensagem do Portfólio de: " + contactRequest.getFirstName() + " " + contactRequest.getLastName());
            helper.setText(htmlBody, true);


            mailSender.send(mimeMessage);

            System.out.println(">>> [EmailService] E-mail entregue ao servidor SMTP com sucesso!");

        }catch (MessagingException | IOException exception){
            exception.printStackTrace();
            throw new RuntimeException("Falha ao enviar o e-mail");
        }
    }

    private String safeReplace(String body, String placeholder, String value) {
        // Se o valor for nulo, usamos uma string vazia. Caso contrário, usamos o valor.
        String replacement = (value == null) ? "" : value;
        return body.replace(placeholder, replacement);
    }
}
