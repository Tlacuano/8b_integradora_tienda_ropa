package mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestBecomeSellerGetByUserEmailDTO {
    @NotBlank(message = "user.email.notnull")
    private String email;
}
