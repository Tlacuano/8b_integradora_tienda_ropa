package mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;

@Data
public class RequestPostShoppingCartDTO {
    private int amount;
    private String idProduct;
    private String idShoppingCart;

    public static RequestPostShoppingCartDTO fromPostShoppingCart(BeanShoppingCart shoppingCart) {
        RequestPostShoppingCartDTO shoppingCartDTO = new RequestPostShoppingCartDTO();
        shoppingCartDTO.setAmount(shoppingCart.getAmount());
        shoppingCartDTO.setIdProduct(shoppingCart.getProduct().getIdProduct().toString());
        shoppingCartDTO.setIdShoppingCart(shoppingCart.getIdShoppingCart().toString());
        return shoppingCartDTO;
    }
}
