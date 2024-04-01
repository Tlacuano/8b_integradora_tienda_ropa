package mx.edu.utez.services_clothing_shop.controller.requests_return_product.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class RequestsReturnGetPageResponseDTO {
    private UUID idRequestReturnProduct;
    private String status;
    private LocalDate orderDate;
    private String orderNumber;
}
