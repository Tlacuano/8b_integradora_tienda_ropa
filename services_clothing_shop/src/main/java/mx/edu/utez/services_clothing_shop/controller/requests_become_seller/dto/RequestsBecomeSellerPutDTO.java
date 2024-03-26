package mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto;


import lombok.Data;

import java.util.UUID;

@Data
public class RequestsBecomeSellerPutDTO {
    private UUID idRequestBecomeSeller;
    private String rejectionReason;
    private String status;
    private UserSellerInformation userSellerInformation;
}
