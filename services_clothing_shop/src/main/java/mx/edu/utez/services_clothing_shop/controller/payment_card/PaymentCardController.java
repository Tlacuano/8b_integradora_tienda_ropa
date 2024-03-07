package mx.edu.utez.services_clothing_shop.controller.payment_card;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.RequestPaymentCardByUserEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.ResponsePaymentCardDTO;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
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
}
