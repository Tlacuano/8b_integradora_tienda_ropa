package mx.edu.utez.services_clothing_shop.controller.order.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrderByUserEmailDTO {
    @NotBlank(message = "user.email.notnull")
    private String email;
    private Pageable page;
}
