package mx.edu.utez.services_clothing_shop.controller.order;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestPostOrderDTO;
import mx.edu.utez.services_clothing_shop.controller.order.dto.ResponseOrderAddressDTO;
import mx.edu.utez.services_clothing_shop.controller.order.dto.ResponseOrderDTO;
import mx.edu.utez.services_clothing_shop.controller.order.dto.RequestOrderByUserEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.ResponsePaymentCardDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import mx.edu.utez.services_clothing_shop.service.order.OrderService;
import org.springframework.data.domain.Page;
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

    @PostMapping("/orders-by-user-email")
    public Page<ResponseOrderDTO> getOrdersByUserEmail(@Valid @RequestBody RequestOrderByUserEmailDTO userEmailPageRequestDTO) {
        Page<BeanOrder> orders = orderService.getOrdersByUserEmail(userEmailPageRequestDTO.getEmail(), userEmailPageRequestDTO.getPage());
        return orders.map(order -> new ResponseOrderDTO().toOrderDTO(order));
    }

    @PostMapping("/post-order")
    public ResponseEntity<ResponseOrderDTO> postOrder(@Valid @RequestBody RequestPostOrderDTO order) {
        try {
            order.setOrderNumber(orderNumberGenerator());
            Map<String, Object> response = orderService.postOrder(order);

            if (response.containsKey("message")) {
                String errorMessage = (String) response.get("message");
                throw new RuntimeException(errorMessage);
            }

            if (!response.isEmpty() && response.containsKey("id_order")) {
                // TODO: Better way to assign values to the DTO?
                ResponseOrderDTO orderDTO = new ResponseOrderDTO();
                orderDTO.setIdOrder(UUID.fromString((String) response.get("id_order")));
                orderDTO.setOrderDate(String.valueOf(order.getOrderDate()));
                orderDTO.setOrderNumber(order.getOrderNumber());

                ResponseOrderAddressDTO orderAddressDTO = new ResponseOrderAddressDTO();
                orderAddressDTO.setIdAddress(UUID.fromString((String) response.get("id_address")));
                orderAddressDTO.setAddress((String) response.get("address"));
                orderAddressDTO.setReferences((String) response.get("references_address"));
                orderAddressDTO.setPostalCode((String) response.get("postal_code"));
                orderAddressDTO.setState((String) response.get("state"));
                orderAddressDTO.setStreet((String) response.get("street"));
                orderAddressDTO.setNeighborhood((String) response.get("neighborhood"));
                orderAddressDTO.setStatus((String) response.get("address_status"));
                orderDTO.setOrderAddress(orderAddressDTO);

                ResponsePaymentCardDTO paymentCardDTO = new ResponsePaymentCardDTO();
                paymentCardDTO.setIdPaymentCard(UUID.fromString((String) response.get("id_payment_card")));
                paymentCardDTO.setCardholderName((String) response.get("cardholder_name"));
                paymentCardDTO.setCardNumber((String) response.get("card_number"));
                paymentCardDTO.setExpirationDate((String) response.get("expiration_date"));
                paymentCardDTO.setStatus((String) response.get("card_status"));
                orderDTO.setPaymentCard(paymentCardDTO);

                return ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);
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
