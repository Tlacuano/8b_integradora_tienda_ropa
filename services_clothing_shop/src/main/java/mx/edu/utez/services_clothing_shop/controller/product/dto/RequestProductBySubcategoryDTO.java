package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestProductBySubcategoryDTO {
    @NotNull(message = "subcategory.subcategory.notnull")
    private String subcategory;

    public RequestProductBySubcategoryDTO() {
    }

    public RequestProductBySubcategoryDTO(String subcategory) {
        this.subcategory = subcategory;
    }
}
