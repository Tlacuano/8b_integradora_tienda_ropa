package mx.edu.utez.services_clothing_shop.controller.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.controller.payment_card.dto.ResponsePaymentCardDTO;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ResponseOrderDTO {
    private UUID idOrder;
    private String orderDate;
    private String orderNumber;

    public ResponseOrderDTO toOrderDTO(BeanOrder order) {
        ResponseOrderDTO dto = new ResponseOrderDTO();
        dto.setIdOrder(order.getIdOrder());
        dto.setOrderDate(order.getOrderDate().toString());
        dto.setOrderNumber(order.getOrderNumber());
        return dto;
    }
}
