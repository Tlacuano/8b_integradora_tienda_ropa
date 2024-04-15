package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestProductBySubcategoryDTO {
    @NotNull(message = "category.category.notnull")
    private String category;
    @NotNull(message = "subcategory.subcategory.notnull")
    private String subcategory;
    private String email;
}
