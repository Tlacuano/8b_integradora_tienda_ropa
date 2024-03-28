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
    private String personSecondLastName;
    private String phoneNumber;
    private UUID statusId;
    private String address;
    private String picture;
    private String status;
    private String userEmail;
    private UserSellerInformation userSellerInformation;
}