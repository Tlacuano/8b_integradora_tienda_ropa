package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class RequestProductByUserEmailDTO {
    @NotBlank(message = "user.email.notnull")
    @Email(message = "user.email.invalid")
    private String email;
    private Pageable page;

    public RequestProductByUserEmailDTO() {
    }

    public RequestProductByUserEmailDTO(String email, Pageable page) {
        this.email = email;
        this.page = page;
    }
}