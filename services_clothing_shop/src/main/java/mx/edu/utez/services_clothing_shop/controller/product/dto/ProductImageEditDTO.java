package mx.edu.utez.services_clothing_shop.controller.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageEditDTO {
    private String image;
    private String status;

}
