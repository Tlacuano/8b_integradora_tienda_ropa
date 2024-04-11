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



    public OrderHasProductsService(IOrderHasProducts orderHasProductsRepository, IUser userRepository, IOrderStatus orderStatusRepository, PasswordEncoder passwordEncoder, EmailService emailService) {
        this.orderHasProductsRepository = orderHasProductsRepository;
        this.userRepository = userRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @Transactional
    public List<BeanOrderHasProducts> getOrdersHasProductsByOrder_IdOrder(UUID idOrder) {
        return orderHasProductsRepository.findAllByOrder_IdOrder(idOrder);
    }

    @Transactional
    public BeanOrderHasProducts getOrdersHasProductsByBuyer(RequestComprobationToReviewDTO payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if(user == null){
            throw new CustomException("user.email.exists");
        }

        BeanOrderHasProducts order = orderHasProductsRepository.findOrderHasProductByBuyerAndProduct(user.getIdUser(),payload.getIdProduct());

        if(order == null){
            throw new CustomException("order.bybuyer.notfound");
        }

        return order;
    }

    @Transactional
    public Object getOrdersHasProductsBySeller(RequestGetPageSalesDTO payload, Pageable pageable) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if(user == null){
            throw new CustomException("user.email.exists");
        }

        BeanOrderStatus status = orderStatusRepository.findByStatus(payload.getStatus());

        if(status == null){
            throw new CustomException("order.status.notfound");
        }

        Page<BeanOrderHasProducts> orders = orderHasProductsRepository.findAllBySellerAndStatus(user.getEmail(), status, pageable);

        return orders.map(ResponseOrdersSalesDTO::toOrderSalesDTO);
    }

    @Transactional
    public boolean cancelSellBySeller(RequestActionBySeller payload) {
        BeanOrderHasProducts order = VerifyAuthorityOnOrder(payload);

        BeanOrderStatus status = orderStatusRepository.findByStatus("Cancelado");

        if(status == null){
            throw new CustomException("status.notFound");
        }

        order.setStatus(status);
        orderHasProductsRepository.save(order);

        order.getProduct().setAmount(order.getProduct().getAmount() + order.getAmount());
        order.getProduct().setStatus(false);

        String cardNumber = EncryptionFunctions.decryptString(order.getOrder().getPaymentCard().getCardNumber());
        String lastFourDigits = cardNumber.substring(cardNumber.length() - 4);

        emailService.sendEmail(order.getOrder().getAddress().getPerson().getUser().getEmail(),
                "Compra cancelada",
                "Compra cancelada",
                "El vendedor ha cancelado la compra de tu producto: " + order.getProduct().getProductName() + " con la cantidad de: " + order.getAmount() + " piezas." +
                        "<br><br>Tu dinero será devuelto en un plazo de 3 a 5 días hábiles a tu tarjeta con número: " + "**** **** **** " + lastFourDigits,
                "");

        return true;
    }

    @Transactional
    public boolean markAsSent(RequestActionBySeller payload) {
        BeanOrderHasProducts order = VerifyAuthorityOnOrder(payload);

        BeanOrderStatus status = orderStatusRepository.findByStatus("Enviado");

        if(status == null){
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

        if(user == null){
            throw new CustomException("user.email.exists");
        }
        Page<BeanOrderHasProducts> orders = orderHasProductsRepository.findBySellerAndOrderNumber(user.getEmail(), "%"+payload.getOrderNumber()+"%", pageable);

        return orders.map(ResponseOrdersSalesDTO::toOrderSalesDTO);
    }

    private BeanOrderHasProducts VerifyAuthorityOnOrder(RequestActionBySeller payload) {
        BeanUser user = userRepository.findByEmail(payload.getEmail());

        if(user == null){
            throw new CustomException("user.email.exists");
        }

        if(payload.getPassword() == null || !passwordEncoder.matches(payload.getPassword(), user.getPassword())){
            throw new CustomException("user.password.incorrect");
        }

        BeanOrderHasProducts order = orderHasProductsRepository.findById(payload.getIdOrderProduct()).orElse(null);

        if(order == null){
            throw new CustomException("order.notfound");
        }

        if(!order.getProduct().getUser().getEmail().equals(user.getEmail())){
            throw new CustomException("order.notfound");
        }

        return order;
    }

    public void putStatusOrderHasProducts(UUID idOrderProduct) {
       orderHasProductsRepository.updateOrderHasProductStatus(idOrderProduct);
    }
}
