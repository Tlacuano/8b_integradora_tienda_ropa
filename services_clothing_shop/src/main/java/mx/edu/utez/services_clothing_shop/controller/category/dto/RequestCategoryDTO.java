package mx.edu.utez.services_clothing_shop.controller.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestCategoryDTO {
    @NotBlank(message = "category.category.notnull")
    @Size(min = 5, max = 30, message = "category.category.size")
    private String category;
    @NotBlank(message = "category.image.notnull")
    @Size(max = 255, message = "category.image.size")
    private String image;
    private boolean status;

    public RequestCategoryDTO() {
    }

    public RequestCategoryDTO(String category, String image, boolean status) {
        this.category = category;
        this.image = image;
        this.status = status;
    }
}
