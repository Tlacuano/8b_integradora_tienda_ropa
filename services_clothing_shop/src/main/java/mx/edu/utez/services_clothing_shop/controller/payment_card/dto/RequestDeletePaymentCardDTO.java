package mx.edu.utez.services_clothing_shop.controller.payment_card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeletePaymentCardDTO {
    @NotBlank(message = "payment.cardNumber.notnull")
    @Size(min = 16, max = 16, message = "payment.cardNumber.size")
    @Pattern(regexp = "\\d+", message = "payment.cardNumber.invalid")
    private String cardNumber;
    @NotBlank(message = "user.email.notnull")
    private String email;
}
