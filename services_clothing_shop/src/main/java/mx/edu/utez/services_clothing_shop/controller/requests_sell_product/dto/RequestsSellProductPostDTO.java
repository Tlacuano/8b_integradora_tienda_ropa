package mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestsSellProductPostDTO {
    @NotNull(message = "product.id.notnull")
    private UUID idProduct;
}
