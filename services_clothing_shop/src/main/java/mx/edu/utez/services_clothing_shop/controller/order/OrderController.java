package mx.edu.utez.services_clothing_shop.controller.order;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestPostOrderDTO;
import mx.edu.utez.services_clothing_shop.controller.order.dto.ResponseOrderAddressDTO;
import mx.edu.utez.services_clothing_shop.controller.order.dto.ResponseOrderDTO;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestOrderByUserEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.ResponsePaymentCardDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.service.order.OrderService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;


@RestController
@RequestMapping("venta-ropa/api/order")
@CrossOrigin(origins = "*")
public class OrderController {
    private final OrderService orderService;
    private static final SecureRandom random = new SecureRandom();

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/get-orders-by-user-email")
    public ResponseEntity<Object> getOrdersByUserEmail(@Valid @RequestBody RequestActionByEmailDTO payload, Pageable pageable) {
        Page<BeanOrder> orders = orderService.getOrdersByUserEmail(payload.getEmail(), pageable);
        Page<ResponseOrderDTO> responseOrders = orders.map(order -> new ResponseOrderDTO().toOrderDTO(order));
        return ResponseEntity.status(HttpStatus.OK).body(new CustomResponse<>(responseOrders, "Orders found", false, HttpStatus.OK.value()));
    }

    @PostMapping("/post-order")
    public ResponseEntity<CustomResponse<ResponseOrderDTO>> postOrder(@Valid @RequestBody RequestPostOrderDTO order) {
        try {
            order.setOrderNumber(orderNumberGenerator());
            Map<String, Object> response = orderService.postOrder(order);

            if (response.containsKey("message")) {
                String errorMessage = (String) response.get("message");
                throw new RuntimeException(errorMessage);
            }

            if (!response.isEmpty() && response.containsKey("id_order")) {
                ResponseOrderDTO orderDTO = new ResponseOrderDTO();
                orderDTO.setIdOrder(UUID.fromString((String) response.get("id_order")));
                orderDTO.setOrderDate(String.valueOf(order.getOrderDate()));
                orderDTO.setOrderNumber(order.getOrderNumber());

                return ResponseEntity.status(HttpStatus.CREATED).body(new CustomResponse<>(orderDTO, "Order created successfully", false, HttpStatus.CREATED.value()));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public String orderNumberGenerator() {
        String timestampPart = Long.toString(Instant.now().toEpochMilli());
        String randomPart = Long.toString(random.nextLong()).replace("-", "0");
        return (timestampPart + randomPart).substring(0, 16);
    }
}
