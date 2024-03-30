package mx.edu.utez.services_clothing_shop.controller.person.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestPutPictureDTO {
    private String picture;
    @NotBlank(message = "user.email.notnull")
    private String email;
}
