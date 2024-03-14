package mx.edu.utez.services_clothing_shop.controller.shopping_cart;

import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.GetShoppingCartDTO;
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
    public ResponseEntity<CustomResponse<List<GetShoppingCartDTO>>> findShoppingCarsByUserEmail(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        if(userEmail == null || userEmail.isEmpty()) {
            return new ResponseEntity<>(new CustomResponse<>(null, "El correo del usuario es requerido", true, 400), HttpStatus.BAD_REQUEST);
        }
        List<BeanShoppingCart> shoppingCarts = shoppingCartServices.findShoppingCartsByUserEmail(userEmail);
        List<GetShoppingCartDTO> shoppingCartDTOs = new ArrayList<>();
        for (BeanShoppingCart cart : shoppingCarts) {
            shoppingCartDTOs.add(GetShoppingCartDTO.fromShoppingCart(cart));
        }
        if (shoppingCartDTOs.isEmpty()) {
            return new ResponseEntity<>(new CustomResponse<>(null,"No se encontraron carritos de compras", true, 404), HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(new CustomResponse<>(shoppingCartDTOs, "ok", false, 200));
        }
    }
    @PostMapping("/post-shopping-cart")
    public ResponseEntity<CustomResponse<List<GetShoppingCartDTO>>> createShoppingCart(@Validated @RequestBody BeanShoppingCart shoppingCart) {
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
                if(shoppingCartId != null){
                    shoppingCartServices.deleteShoppingCartById(shoppingCartId);
                    return new ResponseEntity<>(new CustomResponse<>(null, "Carrito de compras eliminado", false, 200), HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(new CustomResponse<>(null, "El id del carrito de compras es requerido", true, 400), HttpStatus.BAD_REQUEST);
                }
            }catch(Exception e){
                return new ResponseEntity<>(new CustomResponse<>(null, "Error al eliminar el carrito de compras", true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
            }
    }

    @PutMapping("/put-shopping-cart")
    public ResponseEntity<CustomResponse<List<GetShoppingCartDTO>>> updateShoppingCart(@Validated @RequestBody BeanShoppingCart shoppingCart) {
        BeanShoppingCart response = shoppingCartServices.updateShoppingCartById(shoppingCart);
        if (response==null) {
            return ResponseEntity.status(new CustomResponse<>(null, "Error al actualizar el carrito de compras", true, 500).getStatus()).build();
        } else {
            GetShoppingCartDTO updatedShoppingCartDTO = GetShoppingCartDTO.fromShoppingCart(response);
            return new ResponseEntity<>(new CustomResponse<>(Collections.singletonList(updatedShoppingCartDTO),"ok", false, 200),HttpStatus.OK);
        }
    }
}
