package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestProductBySubcategoryDTO {
    @NotNull(message = "category.category.notnull")
    private String category;
    @NotNull(message = "subcategory.subcategory.notnull")
    private String subcategory;

    public RequestProductBySubcategoryDTO() {
    }

    public RequestProductBySubcategoryDTO(String subcategory, String category) {
        this.category = category;
        this.subcategory = subcategory;
    }
}
