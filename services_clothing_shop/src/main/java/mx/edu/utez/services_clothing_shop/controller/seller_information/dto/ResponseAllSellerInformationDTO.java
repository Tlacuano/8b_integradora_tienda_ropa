package mx.edu.utez.services_clothing_shop.controller.seller_information.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAllSellerInformationDTO {
    private String fullName;
    private String curp;
    private boolean privacy;
}
