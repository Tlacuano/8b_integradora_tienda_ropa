package mx.edu.utez.services_clothing_shop.controller.order_status;

import mx.edu.utez.services_clothing_shop.service.order_status.OrderStatusService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("venta-ropa/api/order-status")
@CrossOrigin(origins = "*")
public class OrderStatusController {
    private final OrderStatusService orderStatusService;

    public OrderStatusController(OrderStatusService orderStatusService) {
        this.orderStatusService = orderStatusService;
    }

    @GetMapping("/get-order-status")
    public ResponseEntity<Object> getOrderStatus() {
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(orderStatusService.getAll(), "Order status found", false, HttpStatus.OK.value()));
    }
}
