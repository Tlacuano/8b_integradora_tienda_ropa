package mx.edu.utez.services_clothing_shop.controller.payment_card.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestDeletePaymentCardDTO {
    @NotNull
    private UUID idPaymentCard;
    @NotBlank(message = "user.email.notnull")
    private String email;
}
