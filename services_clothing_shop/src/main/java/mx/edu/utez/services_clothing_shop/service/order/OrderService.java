package mx.edu.utez.services_clothing_shop.service.order;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestPostOrderDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order.IOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.UUID;

@Service
public class OrderService {
    private final IOrder orderRepository;
    private static final SecureRandom random = new SecureRandom();

    public OrderService(IOrder orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<IOrder.OrderProjection> getOrders(Pageable page) {
        return orderRepository.findAllOrdersForAdmin(page);
    }

    @Transactional
    public IOrder.OrderDetailsProjection getOrderDetailsByIdOrder(UUID idOrder) {
        return orderRepository.findOrderDetailsByIdOrder(idOrder);
    }

    @Transactional
    public Page<BeanOrder> getOrdersByUserEmail(String email, Pageable page) {
        return orderRepository.findAllByAddress_Person_User_Email(email, page);
    }

    @Transactional(rollbackOn = Exception.class)
    public void postOrder(RequestPostOrderDTO order) {
        orderRepository.sp_post_order(order.getIdUser().toString(), order.getOrderDate(), order.getIdAddress().toString(), order.getIdPaymentCard().toString(), order.getOrderNumber());
    }

    @Transactional
    public BeanOrder getOrderByOrderNumber(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    public String orderNumberGenerator() {
        String timestampPart = Long.toString(Instant.now().toEpochMilli());
        String randomPart = Long.toString(random.nextLong()).replace("-", "0");
        return (timestampPart + randomPart).substring(0, 16);
    }
}
