package mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;

import java.util.UUID;

@Data
public class ResponsePutShoppingCartDTO {
    @NotNull
    private UUID idShoppingCart;
    @NotBlank(message = "shoppingCart.amount.notnull")
    private int amount;

    public static ResponsePutShoppingCartDTO fromPutShoppingCart(BeanShoppingCart shoppingCart) {
        ResponsePutShoppingCartDTO shoppingCartDTO = new ResponsePutShoppingCartDTO();
        shoppingCartDTO.setIdShoppingCart(shoppingCart.getIdShoppingCart());
        shoppingCartDTO.setAmount(shoppingCart.getAmount());
        return shoppingCartDTO;
    }
}
