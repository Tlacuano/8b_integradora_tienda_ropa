package mx.edu.utez.services_clothing_shop.controller.subcategory.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestSubcategoryBySubcategoryDTO {
    @NotBlank(message = "subcategory.subcategory.notnull")
    private String subcategory;
}
