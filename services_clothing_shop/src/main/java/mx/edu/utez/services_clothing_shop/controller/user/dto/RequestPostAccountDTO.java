package mx.edu.utez.services_clothing_shop.controller.user.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestPostAccountDTO {

    @NotNull(message = "user.email.notnull")
    private String email;
    @NotNull(message = "user.password.notnull")
    private String password;

    @NotNull
    private UUID role;
}
