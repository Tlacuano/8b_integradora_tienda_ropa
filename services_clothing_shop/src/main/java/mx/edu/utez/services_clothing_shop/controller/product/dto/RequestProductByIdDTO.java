package mx.edu.utez.services_clothing_shop.controller.product.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestProductByIdDTO {
    private UUID idProduct;

    public RequestProductByIdDTO() {
    }

    public RequestProductByIdDTO(UUID idProduct) {
        this.idProduct = idProduct;
    }
}
