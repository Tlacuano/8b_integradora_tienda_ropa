package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestProductByIdDTO {
    @NotNull(message = "product.id.notnull")
    private UUID idProduct;

}
