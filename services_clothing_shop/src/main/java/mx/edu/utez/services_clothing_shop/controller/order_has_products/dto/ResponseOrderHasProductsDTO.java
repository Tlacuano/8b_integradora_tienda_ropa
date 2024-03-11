package mx.edu.utez.services_clothing_shop.controller.order_has_products.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseProductDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;

import java.util.UUID;

@Data
public class ResponseOrderHasProductsDTO {
    private ResponseProductDTO product;
    private int amount;
    private String status;

    public ResponseOrderHasProductsDTO() {
    }

    public ResponseOrderHasProductsDTO toOrderHasProductsDTO(BeanOrderHasProducts orderHasProducts) {
        ResponseOrderHasProductsDTO dto = new ResponseOrderHasProductsDTO();
        ResponseProductDTO productDTO = new ResponseProductDTO();
        dto.setProduct(productDTO.toProductDTO(orderHasProducts.getProduct()));
        dto.setAmount(orderHasProducts.getAmount());
        dto.setStatus(orderHasProducts.getStatus().getStatus());
        return dto;
    }
}
