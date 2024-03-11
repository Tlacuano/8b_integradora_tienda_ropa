package mx.edu.utez.services_clothing_shop.controller.order;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.order.dto.ResponseOrderDTO;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestOrderByUserEmailDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.service.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders-by-user-email")
    public Page<ResponseOrderDTO> getOrdersByUserEmail(@Valid @RequestBody RequestOrderByUserEmailDTO userEmailPageRequestDTO) {
        Page<BeanOrder> orders = orderService.getOrdersByUserEmail(userEmailPageRequestDTO.getEmail(), userEmailPageRequestDTO.getPage());
        return orders.map(order -> new ResponseOrderDTO().toOrderDTO(order));
    }

    @PostMapping("/save-order")
    public BeanOrder saveOrder(@RequestBody BeanOrder order) {
        return orderService.saveOrder(order);
    }
}
