package mx.edu.utez.services_clothing_shop.controller.subcategory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestSubcategoryByIdDTO {
    @NotNull(message = "subcategory.id.notnull")
    private UUID idSubcategory;

    public RequestSubcategoryByIdDTO() {
    }

    public RequestSubcategoryByIdDTO(UUID idSubcategory) {
        this.idSubcategory = idSubcategory;
    }
}
