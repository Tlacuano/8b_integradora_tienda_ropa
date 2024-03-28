package mx.edu.utez.services_clothing_shop.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.person.GenderEnum;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestPostAccountDTO {

    @NotBlank(message = "user.email.notnull")
    private String email;

    @NotBlank(message = "user.password.notnull")
    private String password;

    private String roleToAssign;

}
