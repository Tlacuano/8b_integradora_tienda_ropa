package mx.edu.utez.services_clothing_shop.controller.payment_card.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPaymentCardByUserEmailDTO {
    @NotBlank(message = "user.email.notnull")
    @Email(message = "user.email.invalid")
    private String email;
    private Pageable page;
}
