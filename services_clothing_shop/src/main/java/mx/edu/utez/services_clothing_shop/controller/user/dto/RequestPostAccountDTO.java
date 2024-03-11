package mx.edu.utez.services_clothing_shop.controller.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.person.GenderEnum;
import mx.edu.utez.services_clothing_shop.utils.RegexPatterns;

import java.time.LocalDate;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestPostAccountDTO {

    @NotBlank(message = "user.email.notnull")
    private String email;

    @NotBlank(message = "user.password.notnull")
    private String password;

    @NotNull
    private UUID role;

    @NotBlank(message = "person.birthday.notnull")
    private LocalDate birthday;

    @NotBlank(message = "person.name.notnull")
    @Pattern(regexp = RegexPatterns.NAME_REGEX, message = "person.name.pattern")
    private String name;

    @NotBlank(message = "person.lastName.notnull")
    @Pattern(regexp = RegexPatterns.NAME_REGEX, message = "person.lastName.pattern")
    private String lastName;

    @Pattern(regexp = RegexPatterns.NAME_REGEX, message = "person.secondLastName.pattern")
    private String secondLastName;

    @NotBlank(message = "person.phoneNumber.notnull")
    @Pattern(regexp = RegexPatterns.PHONE_REGEX, message = "person.phoneNumber.pattern")
    private String phoneNumber;

    private String picture;

    @NotNull
    private GenderEnum genderEnum;
}
