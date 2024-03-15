package mx.edu.utez.services_clothing_shop.controller.user.dto;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.utils.validations.RegexPatterns;

import java.util.UUID;

@Data
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

    public ResponseShoppingCartUserDTO(){

    }

    public static ResponseShoppingCartUserDTO fromUser(BeanUser user){
        ResponseShoppingCartUserDTO userDTO = new ResponseShoppingCartUserDTO();
        userDTO.setIdUser(user.getIdUser());
        userDTO.setEmail(user.getEmail());
        userDTO.setName(user.getPerson().getName());
        userDTO.setLastName(user.getPerson().getLastName());
        userDTO.setSecondLastName(user.getPerson().getSecondLastName());
        return userDTO;
    }
}
