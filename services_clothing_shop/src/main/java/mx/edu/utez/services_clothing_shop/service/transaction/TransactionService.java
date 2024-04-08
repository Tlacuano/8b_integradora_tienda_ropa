package mx.edu.utez.services_clothing_shop.service.transaction;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Product;
import com.stripe.net.Webhook;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestPostOrderDTO;
import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.ResponseShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.transaction.BeanTransaction;
import mx.edu.utez.services_clothing_shop.model.transaction.ITransaction;
import mx.edu.utez.services_clothing_shop.model.transaction_status.BeanTransactionStatus;
import mx.edu.utez.services_clothing_shop.model.transaction_status.ITransactionStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.service.order.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.model.checkout.Session;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Value("${stripe.api.key}")
    private String stripeKey;
    @Value("${stripe.webhook.secret}")
    private String stripeWebhookSecret;

    private final ITransaction transactionRepository;
    private final ITransactionStatus transactionStatusRepository;
    private final IUser userRepository;
    private final OrderService orderService;


    public TransactionService(ITransaction transactionRepository, ITransactionStatus transactionStatusRepository, IUser userRepository, OrderService orderService) {
        this.transactionRepository = transactionRepository;
        this.transactionStatusRepository = transactionStatusRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeKey;
    }

    @Transactional
    public String createCheckoutSession(List<ResponseShoppingCartDTO> shoppingCart, String email, UUID idAddress, UUID idPaymentCard) throws StripeException {
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

        if (session.getUrl().isEmpty()) {
            session.expire();
            throw new RuntimeException("Error al crear la sesión de pago");
        }

        BeanUser user = userRepository.findByEmail(email);
        BeanTransactionStatus status = transactionStatusRepository.findByStatus("Pendiente");

        RequestPostOrderDTO order = new RequestPostOrderDTO();
        order.setIdUser(user.getIdUser());
        order.setOrderDate(LocalDate.now());
        order.setIdAddress(idAddress);
        order.setIdPaymentCard(idPaymentCard);
        order.setOrderNumber(orderService.orderNumberGenerator());
        orderService.postOrder(order);

        BeanOrder savedOrder = orderService.getOrderByOrderNumber(order.getOrderNumber());

        BeanTransaction transaction = new BeanTransaction();
        transaction.setIdSession(session.getId());
        transaction.setTotal(shoppingCart.stream().mapToDouble(product -> product.getProduct().getPrice() * product.getAmount()).sum());
        transaction.setUser(user);
        transaction.setStatus(status);
        transaction.setPaymentStatus(session.getPaymentStatus());
        transaction.setCheckoutStatus(session.getStatus());
        transaction.setOrder(savedOrder);
        transactionRepository.save(transaction);

        return session.getUrl();
    }

    public void fulfillOrder(String stripeSignature, String payload) throws StripeException {
        if (stripeSignature == null || payload == null) {
            throw new RuntimeException("No se recibio la sesión de pago");
        }

        Event event = Webhook.constructEvent(payload, stripeSignature, stripeWebhookSecret);
        if ("checkout.session.completed".equals(event.getType())) {
            Session sessionEvent = (Session) event.getDataObjectDeserializer().getObject().orElseThrow();
            SessionRetrieveParams params = SessionRetrieveParams.builder()
                    .addExpand("line_items")
                    .build();
            Session sessionData = Session.retrieve(sessionEvent.getId(), params, null);

            BeanTransactionStatus transactionStatus = transactionStatusRepository.findByStatus("Pagado");
            BeanTransaction transaction = transactionRepository.findByIdSession(sessionEvent.getId());
            transaction.setPaymentStatus(sessionData.getPaymentStatus());
            transaction.setCheckoutStatus(sessionData.getStatus());
            transaction.setStatus(transactionStatus);
            transactionRepository.save(transaction);
        }
    }
}
