package mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto;

import lombok.Data;

import java.util.UUID;
@Data
public class RequestsReturnProductPutDTO {
    private UUID requestId;
    private String status;
    private String rejectionReason;
    private String email;
}
