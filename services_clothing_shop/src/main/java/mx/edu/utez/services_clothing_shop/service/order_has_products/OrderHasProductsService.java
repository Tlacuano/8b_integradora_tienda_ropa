package mx.edu.utez.services_clothing_shop.service.order_has_products;

import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.RequestActionBySeller;
import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.RequestGetPageSalesDTO;
import mx.edu.utez.services_clothing_shop.controller.order_has_products.dto.ResponseOrdersSalesDTO;
import mx.edu.utez.services_clothing_shop.controller.review.dto.RequestComprobationToReviewDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_has_products.IOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.order_status.BeanOrderStatus;
import mx.edu.utez.services_clothing_shop.model.order_status.IOrderStatus;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.service.email_service.EmailService;
import mx.edu.utez.services_clothing_shop.utils.exception.CustomException;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderHasProductsService {
    private final IOrderHasProducts orderHasProductsRepository;
    private final IUser userRepository;
    private final IOrderStatus orderStatusRepository;
    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private static final String NO_USER_FOUND = "user.email.exists";
    private static final String CANCELLED = "Compra cancelada";

    public OrderHasProductsService(IOrderHasProducts orderHasProductsRepository, IUser userRepository, IOrderStatus orderStatusRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.orderHasProductsRepository = orderHasProductsRepository;
        this.userRepository = userRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public List<BeanOrderHasProducts> getOrdersHasProductsByOrderIdOrder(UUID idOrder) {
        return orderHasProductsRepository.findAllByOrder_IdOrder(idOrder);
    }

    @Transactional
    public BeanOrderHasProducts getOrdersHasProductsByBuyer(RequestComprobationToReviewDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(NO_USER_FOUND);
        }

        BeanOrderHasProducts order = orderHasProductsRepository.findOrderHasProductByBuyerAndProduct(user.getIdUser(), payload.getIdProduct());

        if (order == null) {
            throw new CustomException("order.bybuyer.notfound");
        }

        return order;
    }

    @Transactional
    public Object getOrdersHasProductsBySeller(RequestGetPageSalesDTO payload, Pageable pageable) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(NO_USER_FOUND);
        }

        BeanOrderStatus status = orderStatusRepository.findByStatus(payload.getStatus());

        if (status == null) {
            throw new CustomException("order.status.notfound");
        }

        Page<BeanOrderHasProducts> orders = orderHasProductsRepository.findAllBySellerAndStatus(user.getEmail(), status, pageable);

        return orders.map(ResponseOrdersSalesDTO::toOrderSalesDTO);
    }

    @Transactional
    public boolean cancelSellBySeller(RequestActionBySeller payload) {
        BeanOrderHasProducts order = verifyAuthorityOnOrder(payload);

        BeanOrderStatus status = orderStatusRepository.findByStatus("Cancelado");

        if (status == null) {
            throw new CustomException("status.notFound");
        }

        order.setStatus(status);
        orderHasProductsRepository.save(order);

        order.getProduct().setAmount(order.getProduct().getAmount() + order.getAmount());
        order.getProduct().setStatus(false);

        String cardNumber = EncryptionFunctions.decryptString(order.getOrder().getPaymentCard().getCardNumber());
        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);

        emailService.sendEmail(order.getOrder().getAddress().getPerson().getUser().getEmail(),
                CANCELLED,
                CANCELLED,
                "El vendedor ha cancelado la compra de tu producto: " + order.getProduct().getProductName() + " con la cantidad de: " + order.getAmount() + " piezas." +
                        "<br><br>Tu dinero será devuelto en un plazo de 3 a 5 días hábiles a tu tarjeta con número: " + "**** **** **** " + lastFourDigits,
                "");

        return true;
    }

    @Transactional
    public boolean cancelSellByBuyer(RequestActionBySeller requestBody) {
        BeanUser user = userRepository.findByEmail(requestBody.getEmail());

        if (user == null) {
            throw new CustomException(NO_USER_FOUND);
        }
        if (requestBody.getPassword() == null || !passwordEncoder.matches(requestBody.getPassword(), user.getPassword())) {
            throw new CustomException("user.password.incorrect");
        }


        BeanOrderHasProducts order = orderHasProductsRepository.findById(requestBody.getIdOrderProduct()).orElse(null);

        if (order == null) {
            throw new CustomException("order.notfound");
        }


        BeanOrderStatus status = orderStatusRepository.findByStatus("Cancelado");

        if (status == null) {
            throw new CustomException("status.notFound");
        }

        order.setStatus(status);
        orderHasProductsRepository.save(order);

        order.getProduct().setAmount(order.getProduct().getAmount() + order.getAmount());

        emailService.sendEmail(order.getProduct().getUser().getEmail(),
                CANCELLED,
                CANCELLED,
                "El comprador ha cancelado la compra de tu producto: " + order.getProduct().getProductName() + " con la cantidad de: " + order.getAmount() + " piezas.",
                "");

        order.getProduct().setAmount(order.getProduct().getAmount() + order.getAmount());
        order.getProduct().setStatus(false);


        String cardNumber = EncryptionFunctions.decryptString(order.getOrder().getPaymentCard().getCardNumber());
        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);

        emailService.sendEmail(user.getEmail(),
                "Compra cancelada",
                "La compra ha sido cancelada",
                "Has cancelado la compra del producto: " + order.getProduct().getProductName() + " con la cantidad de: " + order.getAmount() + " piezas." +
                        "<br><br>Tu dinero será devuelto en un plazo de 3 a 5 días hábiles a tu tarjeta con número: " + "**** **** **** " + lastFourDigits,
                "");

        return true;
    }

    @Transactional
    public boolean markAsSent(RequestActionBySeller payload) {
        BeanOrderHasProducts order = verifyAuthorityOnOrder(payload);

        BeanOrderStatus status = orderStatusRepository.findByStatus("Enviado");

        if (status == null) {
            throw new CustomException("status.notFound");
        }

        order.setStatus(status);

        emailService.sendEmail(order.getOrder().getAddress().getPerson().getUser().getEmail(),
                "Compra enviada",
                "Compra enviada",
                "El vendedor ha marcado como enviado tu producto: " + order.getProduct().getProductName(),
                "");

        return true;
    }

    @Transactional
    public Object getOrdersHasProductsBySellerAndNumber(RequestGetPageSalesDTO payload, Pageable pageable) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(NO_USER_FOUND);
        }
        Page<BeanOrderHasProducts> orders = orderHasProductsRepository.findBySellerAndOrderNumber(user.getEmail(), "%" + payload.getOrderNumber() + "%", pageable);

        return orders.map(ResponseOrdersSalesDTO::toOrderSalesDTO);
    }

    private BeanOrderHasProducts verifyAuthorityOnOrder(RequestActionBySeller payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if (user == null) {
            throw new CustomException(NO_USER_FOUND);
        }

        if (payload.getPassword() == null || !passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            throw new CustomException("user.password.incorrect");
        }

        BeanOrderHasProducts order = orderHasProductsRepository.findById(payload.getIdOrderProduct()).orElse(null);

        if (order == null) {
            throw new CustomException("order.notfound");
        }

        if (!order.getProduct().getUser().getEmail().equals(user.getEmail())) {
            throw new CustomException("order.notfound");
        }

        return order;
    }

    public void putStatusOrderHasProducts(UUID idOrderProduct, String rejectReason) {
        orderHasProductsRepository.updateOrderHasProductStatus(idOrderProduct);

        BeanOrderHasProducts order = orderHasProductsRepository.findByIdOrderProduct(idOrderProduct);

        emailService.sendEmail(order.getOrder().getAddress().getPerson().getUser().getEmail(),
                CANCELLED,
                CANCELLED,
                "Un administrador ha cancelado la compra de tu producto: " + order.getProduct().getProductName() + " con la cantidad de: " + order.getAmount() + " piezas." +
                        "<br><br>El motivo de la cancelación es: " + rejectReason + ".",
                "");
    }


}
