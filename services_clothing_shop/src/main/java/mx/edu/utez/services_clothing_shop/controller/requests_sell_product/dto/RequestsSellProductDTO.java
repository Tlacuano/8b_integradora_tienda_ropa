package mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestsSellProductDTO {
    private UUID idRequestSellProduct;
    private String rejectionReason;
    private UUID idProduct;
    private UUID idStatus;
    private String status;
}
