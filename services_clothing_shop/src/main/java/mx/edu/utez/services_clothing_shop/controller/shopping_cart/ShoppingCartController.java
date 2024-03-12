package mx.edu.utez.services_clothing_shop.controller.shopping_cart;

import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.GetShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.service.sopphing_cart.SopphingCartServices;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("venta-ropa/api/shopping-carts")
public class ShoppingCartController {

    private final SopphingCartServices shoppingCartServices;

    public ShoppingCartController(SopphingCartServices shoppingCartServices) {
        this.shoppingCartServices = shoppingCartServices;
    }

    @PostMapping("/get-shopping-cart")
    public ResponseEntity<CustomResponse<List<GetShoppingCartDTO>>> findShoppingCarsByUserEmail(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        if(userEmail == null || userEmail.isEmpty()) {
            return new ResponseEntity<>(new CustomResponse<>(null, "El correo del usuario es requerido", true, 400), HttpStatus.BAD_REQUEST);
        }
        List<GetShoppingCartDTO> shoppingCarts = shoppingCartServices.findShoppingCartsByUserEmail(userEmail);
        if (shoppingCarts.isEmpty()) {
            return new ResponseEntity<>(new CustomResponse<>(null,"No se encontraron carritos de compras", true, 404), HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(new CustomResponse<>(shoppingCarts, "ok", false, 200));
        }
    }
    @PostMapping("/post-shopping-cart")
    public ResponseEntity<CustomResponse<List<GetShoppingCartDTO>>> createShoppingCart(@RequestBody BeanShoppingCart shoppingCart) {
        BeanShoppingCart response = shoppingCartServices.saveShoppingCar(shoppingCart);
        if (response == null) {
            return ResponseEntity.status(new CustomResponse<>(null, "Error al guardar el carrito de compras", true, 500).getStatus()).build();
        } else {
            GetShoppingCartDTO newShoppingCartDTO = GetShoppingCartDTO.fromShoppingCart(response);
            return ResponseEntity.ok(new CustomResponse<>(Collections.singletonList(newShoppingCartDTO), "ok", false, 200));
        }
    }

    @DeleteMapping("/delete-shopping-cart/{shoppingCartId}")
    public ResponseEntity<CustomResponse<BeanShoppingCart>> deleteShoppingCart(@PathVariable UUID shoppingCartId) {
            try{
                shoppingCartServices.deleteShoppingCartById(shoppingCartId);
                return new ResponseEntity<>(new CustomResponse<>(null, "Carrito de compras eliminado", false, 200), HttpStatus.OK);
            }catch(Exception e){
                return new ResponseEntity<>(new CustomResponse<>(null, "Error al eliminar el carrito de compras", true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @PutMapping("/put-shopping-cart")
    public ResponseEntity<CustomResponse<List<GetShoppingCartDTO>>> updateShoppingCart(@RequestBody BeanShoppingCart shoppingCart) {
        BeanShoppingCart response = shoppingCartServices.updateShoppingCartById(shoppingCart);
        if (response==null) {
            System.out.println("entro aki");
            return ResponseEntity.status(new CustomResponse<>(null, "Error al actualizar el carrito de compras", true, 500).getStatus()).build();
        } else {
            GetShoppingCartDTO updatedShoppingCartDTO = GetShoppingCartDTO.fromShoppingCart(response);
            return new ResponseEntity<>(new CustomResponse<>(Collections.singletonList(updatedShoppingCartDTO),"ok", false, 200),HttpStatus.OK);
        }
    }
}
