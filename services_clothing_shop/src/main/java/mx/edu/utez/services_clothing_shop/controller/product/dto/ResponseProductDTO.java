package mx.edu.utez.services_clothing_shop.controller.product.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;

import java.util.UUID;

@Data
public class ResponseProductDTO {
    private UUID idProduct;
    private String productName;
    private String description;
    private double price;
    private int amount;
    private BeanSubcategory subcategory;
    private boolean status;

    public ResponseProductDTO() {
    }

    public ResponseProductDTO toProductDTO(BeanProduct product) {
        ResponseProductDTO dto = new ResponseProductDTO();
        dto.setIdProduct(product.getIdProduct());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAmount(product.getAmount());
        dto.setSubcategory(product.getSubcategory());
        dto.setStatus(product.isStatus());
        return dto;
    }
}
