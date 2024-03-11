package mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class RequestsBecomeSellerDTO {
    private UUID idRequestBecomeSeller;
    private String rejectionReason;
    private UUID userId;
    private UUID statusId;

}