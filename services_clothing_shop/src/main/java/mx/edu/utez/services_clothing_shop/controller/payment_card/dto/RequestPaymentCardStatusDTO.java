package mx.edu.utez.services_clothing_shop.controller.payment_card.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestPaymentCardStatusDTO {
    private UUID idCard;
    private String status;

    public RequestPaymentCardStatusDTO() {
    }

    public RequestPaymentCardStatusDTO(UUID idCard, String status) {
        this.idCard = idCard;
        this.status = status;
    }
}
