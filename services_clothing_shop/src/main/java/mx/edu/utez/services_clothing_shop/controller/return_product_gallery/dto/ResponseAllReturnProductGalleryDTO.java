package mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseAllReturnProductGalleryDTO {
    private String image;
    private UUID requestReturnProductId;
}
