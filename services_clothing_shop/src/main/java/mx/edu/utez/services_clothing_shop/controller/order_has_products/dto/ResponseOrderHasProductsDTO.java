package mx.edu.utez.services_clothing_shop.controller.order_has_products.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;

import java.util.UUID;

@Data
public class ResponseOrderHasProductsDTO {
    private UUID idProduct;
    private int amount;
    private UUID idStatus;

    public ResponseOrderHasProductsDTO() {
    }

    public ResponseOrderHasProductsDTO toOrderHasProductsDTO(BeanOrderHasProducts orderHasProducts) {
        ResponseOrderHasProductsDTO dto = new ResponseOrderHasProductsDTO();
        dto.setIdProduct(orderHasProducts.getProduct().getIdProduct());
        dto.setAmount(orderHasProducts.getAmount());
        dto.setIdStatus(orderHasProducts.getStatus().getIdStatus());
        return dto;
    }
}
