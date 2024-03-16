package mx.edu.utez.services_clothing_shop.controller.subcategory.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestSubcategoryById {
    @NotNull(message = "subcategory.id.notnull")
    private UUID idSubcategory;

    public RequestSubcategoryById() {
    }

    public RequestSubcategoryById(UUID idSubcategory) {
        this.idSubcategory = idSubcategory;
    }
}
