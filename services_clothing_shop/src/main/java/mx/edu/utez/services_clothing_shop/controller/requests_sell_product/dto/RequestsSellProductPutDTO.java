package mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestsSellProductPutDTO {
    private UUID idRequestSellProduct;
    private String status;
    private String rejectionReason;
    private UUID idProduct;
}
