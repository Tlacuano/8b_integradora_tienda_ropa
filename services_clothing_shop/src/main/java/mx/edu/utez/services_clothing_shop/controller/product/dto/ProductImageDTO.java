package mx.edu.utez.services_clothing_shop.controller.product.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ProductImageDTO {
    private UUID idImage;
    private String image;
    private String status;

    public ProductImageDTO() {
    }

    public ProductImageDTO (UUID idImage, String image, String status) {
        this.idImage = idImage;
        this.image = image;
        this.status = status;
    }
}
