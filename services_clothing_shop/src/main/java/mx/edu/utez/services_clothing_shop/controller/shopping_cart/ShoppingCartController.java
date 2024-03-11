package mx.edu.utez.services_clothing_shop.controller.shopping_cart;

import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.GetShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.service.sopphing_cart.SopphingCartServices;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/shopping-cart")
public class ShoppingCartController {

    private final SopphingCartServices shoppingCartServices;

    public ShoppingCartController(SopphingCartServices shoppingCartServices) {
        this.shoppingCartServices = shoppingCartServices;
    }

    @PostMapping("/get-shopping-cart")
    public ResponseEntity<List<GetShoppingCartDTO>> findShoppingCarsByUserEmail(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        if(userEmail == null || userEmail.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        List<GetShoppingCartDTO> shoppingCarts = shoppingCartServices.findShoppingCartsByUserEmail(userEmail);
        if (shoppingCarts.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(shoppingCarts);
        }
    }
    @PostMapping("/save-new-shopping-cart")
    public ResponseEntity<List<GetShoppingCartDTO>> createShoppingCart(@RequestBody BeanShoppingCart shoppingCart) {
        CustomResponse<BeanShoppingCart> response = shoppingCartServices.saveShoppingCar(shoppingCart);
        if (response.isError()) {
            return ResponseEntity.status(response.getStatus()).build();
        } else {
            GetShoppingCartDTO newShoppingCartDTO = GetShoppingCartDTO.fromShoppingCart(response.getData());
            return ResponseEntity.ok(Collections.singletonList(newShoppingCartDTO));
        }
    }

    @DeleteMapping("/delete-shopping-cart/{shoppingCartId}")
    public CustomResponse<BeanShoppingCart> deleteShoppingCart(@PathVariable UUID shoppingCartId) {
            return shoppingCartServices.deleteShoppingCartById(shoppingCartId);
    }

    @PutMapping("/update-shopping-cart")
    public ResponseEntity<List<GetShoppingCartDTO>> updateShoppingCart(@RequestBody BeanShoppingCart shoppingCart) {
        CustomResponse<BeanShoppingCart> response = shoppingCartServices.updateShoppingCartById(shoppingCart);
        if (response.isError()) {
            return ResponseEntity.status(response.getStatus()).build();
        } else {
            GetShoppingCartDTO updatedShoppingCartDTO = GetShoppingCartDTO.fromShoppingCart(response.getData());
            return ResponseEntity.ok(Collections.singletonList(updatedShoppingCartDTO));
        }
    }
}
