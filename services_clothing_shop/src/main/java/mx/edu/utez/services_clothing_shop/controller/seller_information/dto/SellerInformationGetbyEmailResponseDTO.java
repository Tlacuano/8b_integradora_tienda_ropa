package mx.edu.utez.services_clothing_shop.controller.seller_information.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellerInformationGetbyEmailResponseDTO {
    private String taxIdentificationNumber;
    private String secondaryPhoneNumber;
    private String imageIdentification;
    private String curp;
}
