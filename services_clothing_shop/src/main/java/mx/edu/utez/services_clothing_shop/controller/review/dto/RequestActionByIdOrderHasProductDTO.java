package mx.edu.utez.services_clothing_shop.controller.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class RequestActionByIdOrderHasProductDTO {
    @NotNull(message = "review.idOrderHasProduct.notnull")
    private UUID idOrderHasProduct;


}
