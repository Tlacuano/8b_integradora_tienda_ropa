package mx.edu.utez.services_clothing_shop.controller.subcategory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestSubcategoryByCategoryDTO {
    @NotNull(message = "category.category.notnull")
    private String category;

    public RequestSubcategoryByCategoryDTO() {
    }

    public RequestSubcategoryByCategoryDTO(String category) {
        this.category = category;
    }
}
