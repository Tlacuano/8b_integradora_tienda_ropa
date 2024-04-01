package mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class RequestsReturnProductGetByIdResponseDTO {
    private UUID idRequestReturnProduct;
    private String returnReason;
    private UUID idOrderProduct;
    private UUID idOrder;
    private String orderNumber;
    private Integer amount;
    private String email;
    private String image;
    private Double price;
    private String productName;
}
