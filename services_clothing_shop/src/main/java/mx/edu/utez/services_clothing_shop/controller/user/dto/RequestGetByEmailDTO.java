package mx.edu.utez.services_clothing_shop.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestGetByEmailDTO {
    @NotBlank(message = "user.email.notnull")
    private String email;
}
