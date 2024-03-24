package mx.edu.utez.services_clothing_shop.controller.subcategory.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestPutSubcategoryDTO {
    @NotNull(message = "subcategory.id.notnull")
    private UUID idSubcategory;
    @NotBlank(message = "subcategory.subcategory.notnull")
    @Size(min = 5, max = 30, message = "subcategory.subcategory.size")
    private String subcategory;
    @NotBlank(message = "subcategory.image.notnull")
    @Size(max = 255, message = "subcategory.image.size")
    private String image;
    @NotNull(message = "subcategory.category.notnull")
    private UUID idCategory;

    public RequestPutSubcategoryDTO() {
    }

    public RequestPutSubcategoryDTO(UUID idSubcategory,String subcategory, String image, UUID idCategory) {
        this.idSubcategory = idSubcategory;
        this.subcategory = subcategory;
        this.image = image;
        this.idCategory = idCategory;
    }
}
