package mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetailsDTO {
    private UUID idRequestSellProduct;
    private String userEmail;
    private double price;
    private String description;
    private String productName;
    private String image;
    private UUID productId;
}
