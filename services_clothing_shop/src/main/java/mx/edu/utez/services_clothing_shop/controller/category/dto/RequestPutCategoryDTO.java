package mx.edu.utez.services_clothing_shop.controller.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestPutCategoryDTO {
    @NotNull(message = "category.id.notnull")
    private UUID idCategory;
    @NotBlank(message = "category.category.notnull")
    @Size(min = 5, max = 30, message = "category.category.size")
    private String category;
    @NotBlank(message = "category.image.notnull")
    @Size(max = 255, message = "category.image.size")
    private String image;

    public RequestPutCategoryDTO() {
    }

    public RequestPutCategoryDTO(UUID idCategory, String category, String image) {
        this.idCategory = idCategory;
        this.category = category;
        this.image = image;
    }
}
