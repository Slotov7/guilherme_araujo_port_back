package portfolio.guilhermearaujo.service;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;
import portfolio.guilhermearaujo.dto.RecaptchaResponse;

@Service
public class RecaptchaService {

    private final RestTemplate restTemplate; //chamadas http

    @Value("${google.recaptcha.secret-key}")
    private String apiRecaptcha;


    public RecaptchaService(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    public boolean validateRecaptcha(String recaptchaToken) {

        String url = "https://www.google.com/recaptcha/api/siteverify";

        MultiValueMap<String, String> formData = new LinkedMultiValueMap<String, String>(); //cria um mapa para os dados do formulário
        formData.add("secret", apiRecaptcha);//adiciona a chave secreta da API do reCAPTCHA
        formData.add("response", recaptchaToken); //adiciona o token do reCAPTCHA recebido do cliente

        RecaptchaResponse googleResponse = restTemplate.postForObject(url, formData, RecaptchaResponse.class); //faz a requisição para o Google
        if (googleResponse == null) {
            return false; //se a resposta for nula, retorna falso
        }
        if (!googleResponse.getSuccess()) {
            return false; //se a resposta não for bem-sucedida, retorna falso
        }
        if (googleResponse.getScore() < 0.5) {
            return false; //se a pontuação for menor que 0.5, retorna falso
        }
        return true;
    }
}
