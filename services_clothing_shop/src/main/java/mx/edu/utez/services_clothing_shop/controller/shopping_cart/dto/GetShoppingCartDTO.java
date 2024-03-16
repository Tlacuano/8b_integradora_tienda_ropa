package mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseShoppingCartProductDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.ResponseShoppingCartUserDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;

import java.util.UUID;

@Data
public class GetShoppingCartDTO {
    @NotNull
    private UUID idShoppingCart;
    @NotBlank(message = "shoppingCart.amount.notnull")
    private int amount;
    @NotBlank(message = "shoppingCart.product.notnull")
    private ResponseShoppingCartProductDTO product;
    @NotBlank(message = "shoppingCart.user.notnull")
    private ResponseShoppingCartUserDTO user;

    public static GetShoppingCartDTO fromShoppingCart(BeanShoppingCart shoppingCart) {
        GetShoppingCartDTO shoppingCartDTO = new GetShoppingCartDTO();
        shoppingCartDTO.setIdShoppingCart(shoppingCart.getIdShoppingCart());
        shoppingCartDTO.setAmount(shoppingCart.getAmount());
        shoppingCartDTO.setProduct(ResponseShoppingCartProductDTO.fromProduct(shoppingCart.getProduct()));
        shoppingCartDTO.setUser(ResponseShoppingCartUserDTO.fromUser(shoppingCart.getUser()));
        return shoppingCartDTO;
    }
}
