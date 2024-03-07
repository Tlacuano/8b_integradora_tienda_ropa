package mx.edu.utez.services_clothing_shop.controller.order;

import mx.edu.utez.services_clothing_shop.controller.order.dto.ResponseOrderDTO;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestUserEmailPageRequestDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {
    @Autowired
    private OrderService orderService;

    // TODO: Infinite response loop in address and payment card
    @PostMapping("/orders-by-user-email")
    public Page<ResponseOrderDTO> getOrdersByUserEmail(@RequestBody RequestUserEmailPageRequestDTO userEmailPageRequestDTO) {
        Page<BeanOrder> orders = orderService.getOrdersByUserEmail(userEmailPageRequestDTO.getEmail(), userEmailPageRequestDTO.getPage());
        return orders.map(order -> new ResponseOrderDTO().toOrderDTO(order));
    }

    @PostMapping("/save-order")
    public BeanOrder saveOrder(@RequestBody BeanOrder order) {
        return orderService.saveOrder(order);
    }
}
