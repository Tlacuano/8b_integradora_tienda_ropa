package mx.edu.utez.services_clothing_shop.controller.payment_card.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPaymentCardStatusDTO {
    private UUID idCard;
    private String status;
}
