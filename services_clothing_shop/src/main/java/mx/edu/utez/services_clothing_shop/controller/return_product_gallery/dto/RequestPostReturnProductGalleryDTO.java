package mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RequestPostReturnProductGalleryDTO {
    @NotBlank(message = "returnProductGallery.image.notnull")
    private String image;
    @NotNull(message = "returnProductGallery.requestReturnProductId.notnull")
    private UUID requestReturnProductId;
}
