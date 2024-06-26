package mx.edu.utez.services_clothing_shop.controller.payment_card.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ResponsePaymentCardDTO {
    private UUID idPaymentCard;
    private String cardholderName;
    private String cardNumber;
    private String expirationDate;
    private String status;

    public static ResponsePaymentCardDTO toPaymentCardDTO(BeanPaymentCard paymentCard) {
        ResponsePaymentCardDTO dto = new ResponsePaymentCardDTO();
        dto.setIdPaymentCard(paymentCard.getIdPaymentCard());
        dto.setCardholderName(paymentCard.getCardholderName());
        dto.setCardNumber(EncryptionFunctions.decryptString(paymentCard.getCardNumber()));
        dto.setExpirationDate(EncryptionFunctions.decryptString(paymentCard.getExpirationDate()));
        dto.setStatus(paymentCard.getStatus().getStatus());
        return dto;
    }
}
