package mx.edu.utez.services_clothing_shop.service.transaction;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.param.PriceCreateParams;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.ResponseShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.controller.transaction.dto.RequestTransactionDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;

import java.util.ArrayList;
import java.util.List;


@Service
public class TransactionService {
    @Value("${stripe.api.key}")
    private String stripeKey;

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeKey;
    }

    @Transactional
    public String createCheckoutSession(List<ResponseShoppingCartDTO> shoppingCart) throws StripeException {
        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (ResponseShoppingCartDTO shoppingCartProduct : shoppingCart) {
            ProductCreateParams productParams = ProductCreateParams.builder()
                    .setName(shoppingCartProduct.getProduct().getProductName())
                    .setDescription(shoppingCartProduct.getProduct().getDescription())
                    .addImage(shoppingCartProduct.getProduct().getGallery().get(0).getImage())
                    .build();
            Product product = Product.create(productParams);
            lineItems.add(SessionCreateParams.LineItem.builder()
                    .setQuantity((long) shoppingCartProduct.getAmount())
                    .setPriceData(
                            SessionCreateParams.LineItem.PriceData.builder()
                                    .setCurrency("MXN")
                                    .setUnitAmount((long) (shoppingCartProduct.getProduct().getPrice() * 100))
                                    .setProduct(product.getId())
                                    .build()
                    )
                    .build()
            );
        }

        SessionCreateParams sessionParams = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setSuccessUrl("http://localhost:5173/")
                .setCancelUrl("http://localhost:5173/cancel")
                .setExpiresAt(System.currentTimeMillis() / 1000 + 3600) // 1 hour
                .build();
        Session session = Session.create(sessionParams);
        return session.getUrl();
    }
}
