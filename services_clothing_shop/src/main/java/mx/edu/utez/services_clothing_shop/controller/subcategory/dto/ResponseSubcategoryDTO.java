package mx.edu.utez.services_clothing_shop.controller.subcategory.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.subcategory.BeanSubcategory;

import java.util.UUID;

@Data
public class ResponseSubcategoryDTO {
    private UUID idSubcategory;
    private String subcategory;
    private String image;
    private String category;
    private boolean status;

    public ResponseSubcategoryDTO() {
    }

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
