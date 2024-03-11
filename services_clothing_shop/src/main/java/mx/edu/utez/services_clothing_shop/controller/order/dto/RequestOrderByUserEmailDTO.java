package mx.edu.utez.services_clothing_shop.controller.order.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.data.domain.Pageable;

@Data
public class RequestOrderByUserEmailDTO {
    @NotBlank(message = "user.email.notnull")
    @Email(message = "user.email.invalid")
    private String email;
    private Pageable page;

    public RequestOrderByUserEmailDTO() {
    }

    public RequestOrderByUserEmailDTO(String email, Pageable page) {
        this.email = email;
        this.page = page;
    }
}
