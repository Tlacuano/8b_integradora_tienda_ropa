package mx.edu.utez.services_clothing_shop.controller.category.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestCategoryByIdDTO {
    @NotNull(message = "category.id.notnull")
    private UUID idCategory;

    public RequestCategoryByIdDTO() {
    }

    public RequestCategoryByIdDTO(UUID idCategory) {
        this.idCategory = idCategory;
    }
}
