package mx.edu.utez.services_clothing_shop.controller.order.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;

import java.util.UUID;

@Data
public class ResponseOrderDTO {
    private UUID idOrder;
    private String orderDate;
    private String orderNumber;
    private String idAddress;
    private String idPaymentCard;

    public ResponseOrderDTO() {
    }

    public ResponseOrderDTO toOrderDTO(BeanOrder order) {
        ResponseOrderDTO dto = new ResponseOrderDTO();
        dto.setIdOrder(order.getIdOrder());
        dto.setOrderDate(order.getOrderDate().toString());
        dto.setOrderNumber(order.getOrderNumber());
        dto.setIdAddress(order.getAddress().getIdAddress().toString());
        dto.setIdPaymentCard(order.getPaymentCard().getIdPaymentCard().toString());
        return dto;
    }
}
