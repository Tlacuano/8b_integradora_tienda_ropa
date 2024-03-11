package mx.edu.utez.services_clothing_shop.service.order;

import jakarta.transaction.Transactional;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order.IOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
    public BeanOrder saveOrder(BeanOrder order) {
        return orderRepository.save(order);
    }
}
