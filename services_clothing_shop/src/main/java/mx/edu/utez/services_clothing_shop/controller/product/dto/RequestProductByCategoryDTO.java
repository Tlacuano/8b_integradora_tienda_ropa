package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestProductByCategoryDTO {
    @NotNull(message = "category.category.notnull")
    private String category;

    public RequestProductByCategoryDTO() {
    }

    public RequestProductByCategoryDTO(String category) {
        this.category = category;
    }
}
