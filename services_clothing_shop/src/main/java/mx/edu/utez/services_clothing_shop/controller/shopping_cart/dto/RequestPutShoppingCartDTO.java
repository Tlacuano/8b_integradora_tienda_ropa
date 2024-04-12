package mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPutShoppingCartDTO {
    @NotNull
    private UUID idShoppingCart;
    @NotNull
    private int amount;
}
