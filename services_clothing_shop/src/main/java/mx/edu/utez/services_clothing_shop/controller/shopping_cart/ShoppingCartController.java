package mx.edu.utez.services_clothing_shop.controller.shopping_cart;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShopingCart;
import mx.edu.utez.services_clothing_shop.service.sopphing_cart.SopphingCartServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {
    @Autowired
    private SopphingCartServices shoppingCartServices;

    @GetMapping("/get-all")
    public ResponseEntity<List<BeanShopingCart>> getAllShoppingCarts() {
        List<BeanShopingCart> shoppingCarts = shoppingCartServices.getAllShoppingCarts();
        if (shoppingCarts.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(shoppingCarts);
        }
    }

    @GetMapping("/get-shopping-cart/{userEmail}")
    public ResponseEntity<List<BeanShopingCart>> findShoppingCarsByUserEmail(@PathVariable String userEmail) {
        List<BeanShopingCart> shoppingCarts = shoppingCartServices.findShoppingCartsByUserEmail(userEmail);
        if (shoppingCarts.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(shoppingCarts);
        }
    }
    @PostMapping("/save-new-shopping-cart")
    public ResponseEntity<BeanShopingCart> createShoppingCar(@RequestBody BeanShopingCart shoppingCart) {
        BeanShopingCart savedShoppingCart = shoppingCartServices.saveShoppingCar(shoppingCart);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShoppingCart);
    }
    @DeleteMapping("/delete-shopping-cart/{shoppingCartId}")
    public ResponseEntity<Void> deleteShoppingCartById(@PathVariable UUID shoppingCartId) {
        try {
            shoppingCartServices.deleteShoppingCartById(shoppingCartId);
            return ResponseEntity.noContent().build();
        } catch (EmptyResultDataAccessException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
