package mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestBecomeSellerGetByIdResponseDTO {
    private UUID idRequestBecomeSeller;
    private String rejectionReason;
    private UUID userId;
    private UUID personId;
    private String personName;
    private String personLastName;
    private UUID statusId;
    private String status;
    private UserSellerInformation userSellerInformation;

    public UserSellerInformation getUserSellerInformation() {
        return userSellerInformation;
    }

    public void setUserSellerInformation(UserSellerInformation userSellerInformation) {
        this.userSellerInformation = userSellerInformation;
    }
}