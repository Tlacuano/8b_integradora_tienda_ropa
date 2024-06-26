package mx.edu.utez.services_clothing_shop.controller.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductImageDTO {
    private UUID idImage;
    private String image;
    private String status;

}
