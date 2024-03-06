package mx.edu.utez.services_clothing_shop.controller.order;

import mx.edu.utez.services_clothing_shop.controller.order.dto.UserEmailPageRequestDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // TODO: Infinite response loop in address and payment card
    @PostMapping("/orders-by-user-email")
    public Page<BeanOrder> getOrdersByUserEmail(@RequestBody UserEmailPageRequestDTO userEmailPageRequestDTO) {
        return orderService.getOrdersByUserEmail(userEmailPageRequestDTO.getEmail(), userEmailPageRequestDTO.getPage());
    }
}
