package mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestsReturnProductDTO {
    private UUID idRequestReturnProduct;
    private String rejectionReason;
    private UUID orderHasProductId;
    private UUID statusId;
}
