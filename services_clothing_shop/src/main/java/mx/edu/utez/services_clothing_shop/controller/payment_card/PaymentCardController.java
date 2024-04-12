package mx.edu.utez.services_clothing_shop.controller.payment_card;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.*;
import mx.edu.utez.services_clothing_shop.model.card_status.BeanCardStatus;
import mx.edu.utez.services_clothing_shop.model.payment_card.BeanPaymentCard;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.payment_card.PaymentCardService;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@RestController
@RequestMapping("venta-ropa/api/payment-cards")
@CrossOrigin(origins = "*")
public class PaymentCardController {
    private final PaymentCardService paymentCardService;
    private final UserService userService;

    public PaymentCardController(PaymentCardService paymentCardService, UserService userService) {
        this.paymentCardService = paymentCardService;
        this.userService = userService;
    }

    @PostMapping("/get-payment-card-by-user-email")
    public ResponseEntity<CustomResponse<Page<ResponsePaymentCardDTO>>> getPaymentCardByUserEmail(@Valid @RequestBody RequestPaymentCardByUserEmailDTO requestBody, Pageable page) {
        Page<BeanPaymentCard> paymentCardPage = paymentCardService.getPaymentCardByUserEmail(requestBody.getEmail(), page);
        Page<ResponsePaymentCardDTO> responsePaymentCardPage = paymentCardPage.map(ResponsePaymentCardDTO::toPaymentCardDTO);
        return ResponseEntity.ok(new CustomResponse<>(responsePaymentCardPage, "Tarjetas de crédito encontradas", false, 200));
    }

    @PostMapping("/post-payment-card")
    public ResponseEntity<CustomResponse<ResponsePaymentCardDTO>> postPaymentCard(@Valid @RequestBody RequestPaymentCardDTO paymentCard) throws UnsupportedEncodingException {
        if (paymentCardService.cardIsRegistered(paymentCard.getCardNumber(), paymentCard.getEmail())) {
            throw new CustomException("payment.card.registered");
        }
        BeanPaymentCard beanPaymentCard = new BeanPaymentCard();
        beanPaymentCard.setCardNumber(URLEncoder.encode(EncryptionFunctions.encryptString(paymentCard.getCardNumber()), StandardCharsets.UTF_8));
        beanPaymentCard.setCardholderName(URLEncoder.encode(EncryptionFunctions.encryptString(paymentCard.getCardholderName()), StandardCharsets.UTF_8));
        beanPaymentCard.setExpirationDate(URLEncoder.encode(EncryptionFunctions.encryptString(paymentCard.getExpirationDate()), StandardCharsets.UTF_8));
        beanPaymentCard.setCvv(URLEncoder.encode(EncryptionFunctions.encryptString(paymentCard.getCvv()), StandardCharsets.UTF_8));
        BeanUser user = userService.getByEmail(paymentCard.getEmail());
        beanPaymentCard.setUser(user);
        Integer count = paymentCardService.countPaymentCardByUserEmail(paymentCard.getEmail());
        ResponsePaymentCardDTO responsePaymentCard = ResponsePaymentCardDTO.toPaymentCardDTO(paymentCardService.postPaymentCard(beanPaymentCard, count));
        return new ResponseEntity<>(new CustomResponse<>(responsePaymentCard, "Tarjeta de crédito registrada correctamente", false, 200), HttpStatus.OK);
    }

    @PostMapping("/put-payment-card-status")
    public ResponseEntity<CustomResponse<Boolean>> putPaymentCardStatus(@Valid @RequestBody RequestPaymentCardStatusDTO requestBody) {
        Map<String, Object> response = paymentCardService.putPaymentCardStatus(requestBody.getIdCard(), requestBody.getStatus());
        if (response.containsKey("error_message")) {
            throw new CustomException(response.get("error_message").toString());
        }
        return new ResponseEntity<>(new CustomResponse<>(true, response.get("message").toString(), false, 200), HttpStatus.OK);
    }

    @PostMapping("/delete-payment-card")
    public ResponseEntity<Boolean> deletePaymentCard(@Valid @RequestBody RequestDeletePaymentCardDTO requestBody) {
        if (!paymentCardService.cardIsFromUser(requestBody.getCardNumber(), requestBody.getEmail())) {
            throw new CustomException("payment.card.notFound");
        }
        if (paymentCardService.countPaymentCardByUserEmail(requestBody.getEmail()) <= 1) {
            throw new CustomException("payment.minimum.card");
        }
        paymentCardService.deletePaymentCard(requestBody.getCardNumber(), requestBody.getEmail());
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
