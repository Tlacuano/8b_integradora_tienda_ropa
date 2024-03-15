package mx.edu.utez.services_clothing_shop.controller.payment_card;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.*;
import mx.edu.utez.services_clothing_shop.model.card_status.BeanCardStatus;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.payment_card.PaymentCardService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("venta-ropa/api/payment-card")
@CrossOrigin(origins = "*")
public class PaymentCardController {
    private final PaymentCardService paymentCardService;

    public PaymentCardController(PaymentCardService paymentCardService) {
        this.paymentCardService = paymentCardService;
    }

    @PostMapping("/get-payment-card-by-user-email")
    public ResponseEntity<CustomResponse<Page<ResponsePaymentCardDTO>>> getPaymentCardByUserEmail(@Valid @RequestBody RequestPaymentCardByUserEmailDTO requestBody) {
        Page<BeanPaymentCard> paymentCards = paymentCardService.getPaymentCardByUserEmail(requestBody.getEmail(), requestBody.getPage());
        Page<ResponsePaymentCardDTO> dtoPage = paymentCards.map(paymentCard -> new ResponsePaymentCardDTO().toPaymentCardDTO(paymentCard));
        return new ResponseEntity<>(new CustomResponse<>(dtoPage, "success", false, 200), HttpStatus.OK);
    }

    @PostMapping("/post-payment-card")
    public ResponseEntity<CustomResponse<ResponsePaymentCardDTO>> postPaymentCard(@Valid @RequestBody RequestPaymentCardDTO paymentCard) {
        if (paymentCardService.cardIsRegistered(paymentCard.getCardNumber(), paymentCard.getIdUser())) {
            throw new RuntimeException("payment.card.registered");
        }
        BeanPaymentCard beanPaymentCard = getBeanPaymentCard(paymentCard);
        beanPaymentCard.setCardNumber(EncryptionFunctions.encryptString(paymentCard.getCardNumber()));
        beanPaymentCard.setCardholderName(EncryptionFunctions.encryptString(paymentCard.getCardholderName()));
        beanPaymentCard.setExpirationDate(EncryptionFunctions.encryptString(paymentCard.getExpirationDate()));
        beanPaymentCard.setCvv(EncryptionFunctions.encryptString(paymentCard.getCvv()));
        ResponsePaymentCardDTO responsePaymentCard = new ResponsePaymentCardDTO().toPaymentCardDTO(paymentCardService.postPaymentCard(beanPaymentCard));
        return new ResponseEntity<>(new CustomResponse<>(responsePaymentCard, "Tarjeta de crédito registrada correctamente", false, 200), HttpStatus.OK);
    }

    @PostMapping("/put-payment-card-status")
    public ResponseEntity<CustomResponse<Boolean>> putPaymentCardStatus(@Valid @RequestBody RequestPaymentCardStatusDTO requestBody) {
        try {
            Map<String, Object> response = paymentCardService.putPaymentCardStatus(requestBody.getIdCard(), requestBody.getStatus());
            if (response.containsKey("error_message")) {
                throw new RuntimeException(response.get("error_message").toString());
            }
            return new ResponseEntity<>(new CustomResponse<>(true, response.get("message").toString(), false, 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new CustomResponse<>(false, e.getMessage(), true, 400), HttpStatus.BAD_REQUEST);
        }
    }

    // TODO: Deleting a payment card is necessary, but it can break the app
    @PostMapping("/delete-payment-card")
    public ResponseEntity<Boolean> deletePaymentCard(@Valid @RequestBody RequestDeletePaymentCardDTO requestBody) {
        try {
            if (!paymentCardService.cardIsFromUser(requestBody.getCardNumber(), requestBody.getEmail())) {
                throw new RuntimeException("payment.card.notFound");
            }
            if (paymentCardService.countPaymentCardByUserEmail(requestBody.getEmail()) <= 1) {
                throw new RuntimeException("payment.minimum.card");
            }
            paymentCardService.deletePaymentCard(requestBody.getCardNumber(), requestBody.getEmail());
            return ResponseEntity.status(HttpStatus.OK).body(true);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    private static BeanPaymentCard getBeanPaymentCard(RequestPaymentCardDTO paymentCard) {
        BeanPaymentCard beanPaymentCard = new BeanPaymentCard();
        beanPaymentCard.setCardholderName(EncryptionFunctions.encryptString(paymentCard.getCardholderName()));
        beanPaymentCard.setCardNumber(EncryptionFunctions.encryptString(paymentCard.getCardNumber()));
        beanPaymentCard.setExpirationDate(EncryptionFunctions.encryptString(paymentCard.getExpirationDate()));
        beanPaymentCard.setCvv(EncryptionFunctions.encryptString(paymentCard.getCvv()));

        BeanCardStatus status = new BeanCardStatus();
        status.setIdStatus(paymentCard.getIdStatus());
        beanPaymentCard.setStatus(status);

        BeanUser user = new BeanUser();
        user.setIdUser(paymentCard.getIdUser());
        beanPaymentCard.setUser(user);
        return beanPaymentCard;
    }
}
