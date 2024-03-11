package mx.edu.utez.services_clothing_shop.service.order;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestPostOrderDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order.IOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class OrderService {
    private final IOrder orderRepository;

    public OrderService(IOrder orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<BeanOrder> getOrdersByUserEmail(String email, Pageable page) {
        return orderRepository.findAllByAddress_Person_User_Email(email, page);
    }

    @Transactional(rollbackOn = Exception.class)
    public Map<String, Object> postOrder(RequestPostOrderDTO order) {
        return orderRepository.postOrder(order.getIdUser().toString(), order.getOrderDate(), order.getIdAddress().toString(), order.getIdPaymentCard().toString(), order.getOrderNumber());
    }
}
