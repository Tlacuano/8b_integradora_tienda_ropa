package mx.edu.utez.services_clothing_shop.controller.return_product_gallery.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;
@Data
public class RequestActionByIdDTO {
    @NotNull(message = "returnProductGallery.idImage.notnull")
    private UUID idImage;
}
