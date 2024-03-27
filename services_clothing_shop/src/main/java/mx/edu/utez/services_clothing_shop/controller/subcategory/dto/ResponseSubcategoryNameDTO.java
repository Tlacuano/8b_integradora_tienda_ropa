package mx.edu.utez.services_clothing_shop.controller.subcategory.dto;

import lombok.Data;

@Data
public class ResponseSubcategoryNameDTO {
    private String subcategoryName;

    public ResponseSubcategoryNameDTO() {
    }

    public static ResponseSubcategoryNameDTO toSubcategoryNameDTO(String subcategoryName) {
        ResponseSubcategoryNameDTO dto = new ResponseSubcategoryNameDTO();
        dto.setSubcategoryName(subcategoryName);
        return dto;
    }
}
