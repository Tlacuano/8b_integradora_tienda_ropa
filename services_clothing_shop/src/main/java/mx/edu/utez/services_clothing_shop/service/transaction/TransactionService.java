package mx.edu.utez.services_clothing_shop.service.transaction;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.transaction.dto.RequestTransactionDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;


@Service
public class TransactionService {
    @Value("${stripe.api.key}")
    private String stripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeKey;
    }

    @Transactional
    public String createCheckoutSession(RequestTransactionDTO requestTransactionDTO) throws StripeException {
        SessionCreateParams params = SessionCreateParams.builder()
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:8080/")
                .setCancelUrl("http://localhost:8080/bad")
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setQuantity(1L)
                                .setPrice("price_1P2zx706qgqr23mAE5hyDCjj")
                                .build()
                )
                .build();
        Session session = Session.create(params);
        return session.getUrl();
    }
}
