package mx.edu.utez.services_clothing_shop.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestCodeDTO {
    @NotBlank
    private String email;
    @NotBlank
    private String code;
}
