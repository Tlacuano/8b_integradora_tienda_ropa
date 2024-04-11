package mx.edu.utez.services_clothing_shop.controller.order_has_products.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseProductDTO;
import mx.edu.utez.services_clothing_shop.model.order_has_products.BeanOrderHasProducts;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ResponseOrderHasProductsDTO {
    private UUID idOrderProduct;
    private ResponseProductDTO product;
    private int amount;
    private String status;

    public static ResponseOrderHasProductsDTO toOrderHasProductsDTO(BeanOrderHasProducts orderHasProducts) {
        ResponseOrderHasProductsDTO dto = new ResponseOrderHasProductsDTO();
        dto.setIdOrderProduct(orderHasProducts.getIdOrderProduct());
        dto.setProduct(ResponseProductDTO.toProductDTO(orderHasProducts.getProduct()));
        dto.setAmount(orderHasProducts.getAmount());
        dto.setStatus(orderHasProducts.getStatus().getStatus());
        return dto;
    }
}
