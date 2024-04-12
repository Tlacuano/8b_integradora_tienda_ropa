package mx.edu.utez.services_clothing_shop.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseShoppingCartUserDTO {
    @NotNull
    private UUID idUser;
    @NotBlank(message = "user.email.notnull")
    @Pattern(regexp = RegexPatterns.EMAIL_REGEX, message = "user.email.pattern")
    private String email;
    @NotBlank(message = "person.name.notnull")
    @Pattern(regexp = RegexPatterns.NAME_REGEX, message = "person.name.pattern")
    private String name;
    @NotBlank(message = "person.lastName.notnull")
    @Pattern(regexp = RegexPatterns.NAME_REGEX, message = "person.lastName.pattern")
    private String lastName;
    @Pattern(regexp = RegexPatterns.NAME_REGEX, message = "person.secondLastName.pattern")
    private String secondLastName;
}
