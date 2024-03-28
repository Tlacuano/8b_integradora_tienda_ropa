package mx.edu.utez.services_clothing_shop.controller.seller_information.dto;

import lombok.Data;

@Data
public class ResponseAllSellerInformationDTO {
    private String fullName;
    private String curp;
    private boolean privacy;

    public ResponseAllSellerInformationDTO() {
    }

}
