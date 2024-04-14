package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestProductByNameDTO {
    @NotBlank(message = "product.name.notnull")
    private String productName;
    @NotBlank(message = "user.email.notnull")
    private String userEmail;
}
