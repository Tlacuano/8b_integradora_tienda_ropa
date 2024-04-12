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
public class RequestPaymentCardDTO {
    @NotBlank(message = "payment.cardholderName.notnull")
    private String cardholderName;
    @NotBlank(message = "payment.cardNumber.notnull")
    @Size(min = 16, max = 16, message = "payment.cardNumber.size")
    @Pattern(regexp = "\\d+", message = "payment.cardNumber.invalid")
    private String cardNumber;
    @NotBlank(message = "payment.expirationDate.notnull")
    @Pattern(regexp = "(0[1-9]|1[0-2])/(\\d{2})", message = "payment.expirationDate.invalid")
    private String expirationDate;
    @NotBlank(message = "payment.cvv.notnull")
    @Pattern(regexp = "\\d{3}", message = "payment.cvv.invalid")
    @Size(min = 3, max = 4, message = "payment.cvv.size")
    private String cvv;
    @NotBlank(message = "user.email.notnull")
    private String email;
}
