package mx.edu.utez.services_clothing_shop.controller.payment_card;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.RequestDeletePaymentCardDTO;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.RequestPaymentCardByUserEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.RequestPaymentCardDTO;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.ResponsePaymentCardDTO;
import mx.edu.utez.services_clothing_shop.model.card_status.BeanCardStatus;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.payment_card.PaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("venta-ropa/api/payment-card")
@CrossOrigin(origins = "*")
public class PaymentCardController {
    @Autowired
    private PaymentCardService paymentCardService;

    @PostMapping("/get-payment-card-by-user-email")
    public ResponseEntity<Page<ResponsePaymentCardDTO>> getPaymentCardByUserEmail(@Valid @RequestBody RequestPaymentCardByUserEmailDTO requestBody) {
        Page<BeanPaymentCard> paymentCards = paymentCardService.getPaymentCardByUserEmail(requestBody.getEmail(), requestBody.getPage());
        Page<ResponsePaymentCardDTO> dtoPage = paymentCards.map(paymentCard -> new ResponsePaymentCardDTO().toPaymentCardDTO(paymentCard));
        return ResponseEntity.status(HttpStatus.OK).body(dtoPage);
    }

    @PostMapping("/post-payment-card")
    public ResponseEntity<RequestPaymentCardDTO> postPaymentCard(@Valid @RequestBody RequestPaymentCardDTO paymentCard) {

        if (paymentCardService.cardIsRegistered(paymentCard.getCardNumber(), paymentCard.getIdUser())) {
            throw new RuntimeException("payment.card.registered");
        }

        BeanPaymentCard beanPaymentCard = new BeanPaymentCard();
        beanPaymentCard.setIdPaymentCard(paymentCard.getIdPaymentCard());
        beanPaymentCard.setCardholderName(paymentCard.getCardholderName());
        beanPaymentCard.setCardNumber(paymentCard.getCardNumber());
        beanPaymentCard.setExpirationDate(paymentCard.getExpirationDate());
        beanPaymentCard.setCvv(paymentCard.getCvv());

        BeanCardStatus status = new BeanCardStatus();
        status.setIdStatus(paymentCard.getIdStatus());
        beanPaymentCard.setStatus(status);

        BeanUser user = new BeanUser();
        user.setIdUser(paymentCard.getIdUser());
        beanPaymentCard.setUser(user);

        paymentCardService.postPaymentCard(beanPaymentCard);

        return ResponseEntity.status(HttpStatus.OK).body(paymentCard);
    }

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
}
