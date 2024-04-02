package mx.edu.utez.services_clothing_shop.controller.order_has_products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestOrderHasProductsByOrderIdDTO {
    private UUID idOrder;

    public RequestOrderHasProductsByOrderIdDTO(UUID idOrder) {
        this.idOrder = idOrder;
    }
}
