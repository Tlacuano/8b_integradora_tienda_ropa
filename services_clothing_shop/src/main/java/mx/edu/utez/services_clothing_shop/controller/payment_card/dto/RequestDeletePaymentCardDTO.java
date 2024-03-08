package mx.edu.utez.services_clothing_shop.controller.payment_card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestDeletePaymentCardDTO {
    @NotBlank(message = "payment.cardNumber.notnull")
    @Size(min = 16, max = 16, message = "payment.cardNumber.size")
    @Pattern(regexp = "\\d+", message = "payment.cardNumber.invalid")
    private String cardNumber;
    @NotBlank(message = "user.email.notnull")
    private String email;

    public RequestDeletePaymentCardDTO() {
    }

    public RequestDeletePaymentCardDTO(String cardNumber, String email) {
        this.cardNumber = cardNumber;
        this.email = email;
    }
}
