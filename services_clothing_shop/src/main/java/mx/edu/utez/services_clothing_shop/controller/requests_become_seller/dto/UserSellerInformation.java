package mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto;

import lombok.Data;

@Data
public class UserSellerInformation {
    private String taxIdentificationNumber;
    private String secondaryPhoneNumber;
    private Boolean privacyPolicyAgreement;
    private String imageIdentification;
    private String curp;
}
