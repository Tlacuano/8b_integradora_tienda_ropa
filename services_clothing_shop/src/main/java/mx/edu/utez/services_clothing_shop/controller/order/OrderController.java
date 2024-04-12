package mx.edu.utez.services_clothing_shop.controller.order;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.order.dto.*;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.model.order.IOrder;
import mx.edu.utez.services_clothing_shop.service.order.OrderService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("venta-ropa/api/orders")
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/get-orders")
    public ResponseEntity<Object> getOrders(Pageable page) {
        Page<IOrder.OrderProjection> orders = orderService.getOrders(page);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(orders, "Orders found", false, HttpStatus.OK.value()));
    }

    @PostMapping("/get-orders-by-order-number")
    public ResponseEntity<Object> getOrdersByOrderNumber(Pageable pageable, @Valid @RequestBody RequestOrderByOrderNumberDTO payload, BindingResult result) {
        if (result.hasErrors()) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new CustomResponse<>(null, "Invalid data", true, HttpStatus.BAD_REQUEST.value()));
        Page<IOrder.OrderProjection> orders = orderService.getOrdersByOrderNumber(payload.getOrderNumber(), pageable);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(orders, "Orders by number found", false, HttpStatus.OK.value()));
    }

    @PostMapping("/get-order-details")
    public ResponseEntity<Object> getOrderDetailsByIdOrder(@RequestBody RequestOrderByIdOrderDTO payload) {
        IOrder.OrderDetailsProjection orderDetails = orderService.getOrderDetailsByIdOrder(payload.getIdOrder());
        ResponseOrderDetailsDTO responseOrderDetails = ResponseOrderDetailsDTO.toOrderDetailsDTO(orderDetails);
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(responseOrderDetails, "Order details found", false, HttpStatus.OK.value()));
    }

    @PostMapping("/get-orders-by-user-email")
    public ResponseEntity<Object> getOrdersByUserEmail(@Valid @RequestBody RequestActionByEmailDTO payload, Pageable pageable) {
        Page<BeanOrder> orders = orderService.getOrdersByUserEmail(payload.getEmail(), pageable);
        Page<ResponseOrderDTO> responseOrders = orders.map(order -> new ResponseOrderDTO().toOrderDTO(order));
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(responseOrders, "Orders by email found", false, HttpStatus.OK.value()));
    }

    @PostMapping("/post-order")
    public ResponseEntity<CustomResponse<Boolean>> postOrder(@Valid @RequestBody RequestPostOrderDTO order) {
        try {
            orderService.postOrder(order);
            return ResponseEntity.ok(new CustomResponse<>(true, "Order created", false, HttpStatus.OK.value()));
        } catch (
                Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
