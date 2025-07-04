package portfolio.guilhermearaujo.dto;

import lombok.Data;

@Data
public class RecaptchaResponse {
    private Boolean success;
    private Double score;
}
