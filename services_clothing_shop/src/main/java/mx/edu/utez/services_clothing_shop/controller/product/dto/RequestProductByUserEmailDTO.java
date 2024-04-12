package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestProductByUserEmailDTO {
    @NotBlank(message = "user.email.notnull")
    @Email(message = "user.email.invalid")
    private String email;
}
