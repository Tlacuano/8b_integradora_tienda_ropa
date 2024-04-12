package mx.edu.utez.services_clothing_shop.controller.order_has_products.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestOrderHasProductByIdDTO {
    private UUID idOrderProduct;
    @NotBlank
    private String rejectReason;
}
