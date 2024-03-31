package mx.edu.utez.services_clothing_shop.controller.shopping_cart;

import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.*;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.service.shopping_cart.ShoppingCartServices;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("venta-ropa/api/shopping-carts")
public class ShoppingCartController {

    private final ShoppingCartServices shoppingCartServices;

    public ShoppingCartController(ShoppingCartServices shoppingCartServices) {
        this.shoppingCartServices = shoppingCartServices;
    }


    @PostMapping("/get-shopping-cart")
    public ResponseEntity<Object> findShoppingCarsByUserEmail(@Validated @RequestBody RequestActionByEmailDTO payload) {
        List<ResponseShoppingCartDTO> shoppingCarts = shoppingCartServices.findShoppingCartsByUserEmail(payload.getEmail());
        return ResponseEntity.ok(new CustomResponse<>(shoppingCarts, "ok", false, 200));
    }

    @PostMapping("/post-shopping-cart")
    public ResponseEntity<Object> createShoppingCart(@Validated @RequestBody RequestPostCartDTO shoppingCart) {
        return ResponseEntity.ok(new CustomResponse<>(shoppingCartServices.saveShoppingCar(shoppingCart), "ok", false, 200));
    }

    @PostMapping("/delete-shopping-cart")
    public ResponseEntity<Object> deleteShoppingCart(@Validated @RequestBody RequestDeleteShoppingCartDTO shoppingCartId) {
        shoppingCartServices.deleteShoppingCartById(shoppingCartId.getIdShoppingCart());
        return new ResponseEntity<>(new CustomResponse<>(null, "Carrito de compras eliminado", false, 200), HttpStatus.OK);
    }

    @PostMapping("/put-shopping-cart")
    public ResponseEntity<Object> updateShoppingCart(@Validated @RequestBody RequestPutShoppingCartDTO shoppingCart) {
        ResponsePutShoppingCartDTO response = shoppingCartServices.updateShoppingCartById(shoppingCart);
        return new ResponseEntity<>(new CustomResponse<>(Collections.singletonList(response), "ok", false, 200), HttpStatus.OK);

    }
}



