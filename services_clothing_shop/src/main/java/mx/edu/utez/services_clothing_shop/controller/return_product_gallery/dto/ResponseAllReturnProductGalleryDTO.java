package mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponseAllReturnProductGalleryDTO {
    private String image;
    private UUID requestReturnProductId;

    public ResponseAllReturnProductGalleryDTO() {
    }

}
