package mx.edu.utez.services_clothing_shop.controller.twilio.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class sendSmsDTO {
    @NotBlank
    String to;

    @NotBlank
    String email;
}
