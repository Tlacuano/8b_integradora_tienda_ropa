package mx.edu.utez.services_clothing_shop.controller.payment_card;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.RequestPaymentCardByUserEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.RequestPaymentCardDTO;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.ResponsePaymentCardDTO;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.status.BeanStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.payment_card.PaymentCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-card")
@CrossOrigin(origins = "*")
public class PaymentCardController {
    @Autowired
    private PaymentCardService paymentCardService;

    @PostMapping("/payment-card-by-user-email")
    public Page<ResponsePaymentCardDTO> getPaymentCardByUserEmail(@Valid @RequestBody RequestPaymentCardByUserEmailDTO requestBody) {
        Page<BeanPaymentCard> paymentCards = paymentCardService.getPaymentCardByUserEmail(requestBody.getEmail(), requestBody.getPage());
        return paymentCards.map(paymentCard -> new ResponsePaymentCardDTO().toPaymentCardDTO(paymentCard));
    }

    @PostMapping("/save-payment-card")
    public BeanPaymentCard savePaymentCard(@Valid @RequestBody RequestPaymentCardDTO paymentCard) {
        BeanPaymentCard beanPaymentCard = new BeanPaymentCard();
        beanPaymentCard.setIdPaymentCard(paymentCard.getIdPaymentCard());
        beanPaymentCard.setCardholderName(paymentCard.getCardholderName());
        beanPaymentCard.setCardNumber(paymentCard.getCardNumber());
        beanPaymentCard.setExpirationDate(paymentCard.getExpirationDate());
        beanPaymentCard.setCvv(paymentCard.getCvv());

        BeanStatus status = new BeanStatus();
        status.setIdStatus(paymentCard.getIdStatus());
        beanPaymentCard.setStatus(status);

        BeanUser user = new BeanUser();
        user.setIdUser(paymentCard.getIdUser());
        beanPaymentCard.setUser(user);

        return paymentCardService.savePaymentCard(beanPaymentCard);
    }
}
