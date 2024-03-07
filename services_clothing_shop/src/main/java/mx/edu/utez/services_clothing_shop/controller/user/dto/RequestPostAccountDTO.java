package mx.edu.utez.services_clothing_shop.controller.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestPostAccountDTO {
    @NotNull(message = "El email es obligatorio")
    private String email;
    @NotNull(message = "La contraseña es obligatoria")
    private String password;
}
