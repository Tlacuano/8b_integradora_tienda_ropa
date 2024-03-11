package mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseShoppingCartProductDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;

import java.util.UUID;

@Data
public class GetShoppingCartDTO {
    private UUID idShoppingCart;
    private int amount;
    private ResponseShoppingCartProductDTO product;

    public static GetShoppingCartDTO fromShoppingCart(BeanShoppingCart shoppingCart) {
        GetShoppingCartDTO shoppingCartDTO = new GetShoppingCartDTO();
        shoppingCartDTO.setIdShoppingCart(shoppingCart.getIdShoppingCart());
        shoppingCartDTO.setAmount(shoppingCart.getAmount());
        shoppingCartDTO.setProduct(ResponseShoppingCartProductDTO.fromProduct(shoppingCart.getProduct()));
        return shoppingCartDTO;
    }
}
