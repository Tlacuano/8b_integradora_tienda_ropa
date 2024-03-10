package mx.edu.utez.services_clothing_shop.controller.order_has_products.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Data
public class RequestOrderHasProductsByOrderIdDTO {
    private UUID idOrder;
    private Pageable page;

    public RequestOrderHasProductsByOrderIdDTO() {
    }

    public RequestOrderHasProductsByOrderIdDTO(UUID idOrder, Pageable page) {
        this.idOrder = idOrder;
        this.page = page;
    }
}
