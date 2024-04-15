package mx.edu.utez.services_clothing_shop.service.transaction;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.Product;
import com.stripe.net.Webhook;
import com.stripe.param.ProductCreateParams;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionExpireParams;
import com.stripe.param.checkout.SessionRetrieveParams;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestPostOrderDTO;
import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.ResponseShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order_has_products.IOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.transaction.BeanTransaction;
import mx.edu.utez.services_clothing_shop.model.transaction.ITransaction;
import mx.edu.utez.services_clothing_shop.model.transaction_status.BeanTransactionStatus;
import mx.edu.utez.services_clothing_shop.model.transaction_status.ITransactionStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.service.order.OrderService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
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
    private final IOrderHasProducts orderHasProductsRepository;
    private final OrderService orderService;
    private final EmailService emailService;

    public TransactionService(ITransaction transactionRepository, ITransactionStatus transactionStatusRepository, IUser userRepository, OrderService orderService, IOrderHasProducts orderHasProductsRepository, EmailService emailService) {
        this.transactionRepository = transactionRepository;
        this.transactionStatusRepository = transactionStatusRepository;
        this.userRepository = userRepository;
        this.orderService = orderService;
        this.orderHasProductsRepository = orderHasProductsRepository;
        this.emailService = emailService;
    }

    @PostConstruct
    public void init() {
        Stripe.apiKey = stripeKey;
    }

    @Transactional
    public String createCheckoutSession(List<ResponseShoppingCartDTO> shoppingCart, String email, UUID idAddress, UUID idPaymentCard) throws StripeException {
        for (ResponseShoppingCartDTO shoppingCartProduct : shoppingCart) {
            if (shoppingCartProduct.getAmount() > shoppingCartProduct.getProduct().getAmount()) {
                throw new CustomException("transaction.product.stock");
            }
        }

        List<SessionCreateParams.LineItem> lineItems = new ArrayList<>();
        for (ResponseShoppingCartDTO shoppingCartProduct : shoppingCart) {
            ProductCreateParams productParams = ProductCreateParams.builder()
                    .setName(shoppingCartProduct.getProduct().getProductName())
                    .setDescription(shoppingCartProduct.getProduct().getDescription())
                    .addImage(shoppingCartProduct.getProduct().getGallery().get(0).getImage()).build();
            Product product = Product.create(productParams);
            lineItems.add(SessionCreateParams.LineItem.builder()
                    .setQuantity((long) shoppingCartProduct.getAmount())
                    .setPriceData(SessionCreateParams.LineItem.PriceData.builder()
                            .setCurrency("MXN")
                            .setUnitAmount((long) (shoppingCartProduct.getProduct().getPrice() * 100))
                            .setProduct(product.getId()).build()).build());
        }

        SessionCreateParams sessionParams = SessionCreateParams.builder()
                .addAllLineItem(lineItems)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setSuccessUrl("http://localhost:5173/my-orders")
                .setCancelUrl("http://localhost:5173/")
                .setExpiresAt(System.currentTimeMillis() / 1000 + 3600) // 1 hour
                .build();
        Session session = Session.create(sessionParams);

        if (session.getUrl().isEmpty()) {
            SessionExpireParams expireParams = SessionExpireParams.builder().build();
            session.expire(expireParams);
            throw new CustomException("transaction.session.failed");
        }

        BeanUser user = userRepository.findByEmail(email);
        if (user == null) {
            throw new CustomException("transaction.user.notfound");
        }

        RequestPostOrderDTO order = new RequestPostOrderDTO(user.getIdUser(), LocalDate.now(), idAddress, idPaymentCard, orderService.orderNumberGenerator());
        orderService.postOrder(order);

        BeanOrder savedOrder = orderService.getOrderByOrderNumber(order.getOrderNumber());
        double total = shoppingCart.stream().mapToDouble(product -> product.getProduct().getPrice() * product.getAmount()).sum();

        BeanTransactionStatus status = transactionStatusRepository.findByStatus("Pendiente");
        BeanTransaction transaction = new BeanTransaction(session.getId(), total, user, savedOrder, status, session.getPaymentStatus(), session.getStatus());
        transactionRepository.save(transaction);

        return session.getUrl();
    }

    @Transactional
    public void fulfillOrder(String stripeSignature, String payload) throws StripeException {
        if (stripeSignature == null || payload == null) {
            throw new CustomException("transaction.session.notfound");
        }

        Event event = Webhook.constructEvent(payload, stripeSignature, stripeWebhookSecret);
        Session sessionEvent = (Session) event.getDataObjectDeserializer().getObject().orElseThrow();
        SessionRetrieveParams params = SessionRetrieveParams.builder().addExpand("line_items").build();
        Session sessionData = Session.retrieve(sessionEvent.getId(), params, null);
        BeanTransaction transaction = transactionRepository.findByIdSession(sessionEvent.getId());

        if (transaction == null) {
            throw new CustomException("transaction.notfound");
        }

        if ("checkout.session.completed".equals(event.getType())) {
            BeanTransactionStatus transactionStatus = transactionStatusRepository.findByStatus("Pagado");
            transaction.setPaymentStatus(sessionData.getPaymentStatus());
            transaction.setCheckoutStatus(sessionData.getStatus());
            transaction.setStatus(transactionStatus);
            transactionRepository.save(transaction);

            BeanOrder order = transaction.getOrder();
            orderHasProductsRepository.sp_fulfill_order(order.getIdOrder(), order.getPaymentCard().getUser().getIdUser());

            emailService.sendEmail(order.getPaymentCard().getUser().getEmail(), "Compra realizada", "Su compra ha sido realizada con éxito", "El número de su orden es:", order.getOrderNumber());
        }

        if ("checkout.session.expired".equals(event.getType())) {
            BeanTransactionStatus transactionStatus = transactionStatusRepository.findByStatus("Cancelado");
            transaction.setPaymentStatus(sessionData.getPaymentStatus());
            transaction.setCheckoutStatus(sessionData.getStatus());
            transaction.setStatus(transactionStatus);
            transactionRepository.save(transaction);
        }
    }
}
