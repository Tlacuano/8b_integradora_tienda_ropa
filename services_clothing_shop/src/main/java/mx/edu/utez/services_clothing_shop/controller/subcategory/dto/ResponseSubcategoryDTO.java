package mx.edu.utez.services_clothing_shop.controller.subcategory.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseSubcategoryDTO {
    private UUID idSubcategory;
    private String subcategory;
    private String image;
    private String category;
    private boolean status;

    public static ResponseSubcategoryDTO toSubcategoryDTO(BeanSubcategory subcategory) {
        ResponseSubcategoryDTO dto = new ResponseSubcategoryDTO();
        dto.setIdSubcategory(subcategory.getIdSubcategory());
        dto.setSubcategory(subcategory.getSubcategory());
        dto.setImage(subcategory.getImage());
        dto.setCategory(subcategory.getCategory().getCategory());
        dto.setStatus(subcategory.isStatus());
        return dto;
    }
}
