package mx.edu.utez.services_clothing_shop.controller.wish_list;

import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.ResponsePutShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.controller.shopping_cart.dto.ResponseShoppingCartDTO;
import mx.edu.utez.services_clothing_shop.controller.wish_list.dto.ResponseInformationWishListDTO;
import mx.edu.utez.services_clothing_shop.controller.wish_list.dto.ResponsePutWishListDTO;
import mx.edu.utez.services_clothing_shop.controller.wish_list.dto.ResponseWishListDTO;
import mx.edu.utez.services_clothing_shop.model.shopping_cart.BeanShoppingCart;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;
import mx.edu.utez.services_clothing_shop.service.wish_list.WishListService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("venta-ropa/api/wishes-list")
public class WishListController {

    private final WishListService wishListService;

    public WishListController(WishListService wishListService) {
        this.wishListService = wishListService;
    }

    @PostMapping("/get-wish-list")
    public ResponseEntity<CustomResponse<List<ResponseInformationWishListDTO>>> findWishListByUserEmail(@Validated @RequestBody Map<String, String> requestBody) {
        List<ResponseInformationWishListDTO> wishList = wishListService.findWishListByUserEmail(requestBody.get("userEmail"));
        return ResponseEntity.ok(new CustomResponse<>(wishList, "ok", false, 200));
    }

    @PostMapping("/post-wish-list")
    public ResponseEntity<CustomResponse<List<ResponseWishListDTO>>> createShoppingCart(@Validated @RequestBody BeanWishList wishList) {
        ResponseWishListDTO response = wishListService.saveWishList(wishList);
        return ResponseEntity.ok(new CustomResponse<>(Collections.singletonList(response), "ok", false, 200));
    }

    @PostMapping("/delete-wish-list")
    public ResponseEntity<CustomResponse<BeanWishList>> deleteWishList(@Validated @RequestBody UUID wishListId) {
        wishListService.deleteWishListById(wishListId);
        return new ResponseEntity<>(new CustomResponse<>(null, "Carrito de compras eliminado", false, 200), HttpStatus.OK);
    }

    @PutMapping("/put-wish-list")
    public ResponseEntity<CustomResponse<List<ResponsePutWishListDTO>>> updateWishListById(@Validated @RequestBody BeanWishList wishList) {
        BeanWishList response = wishListService.updateWishListById(wishList);
        ResponsePutWishListDTO updatedWishListDTO = ResponsePutWishListDTO.fromWishList(response);
        return new ResponseEntity<>(new CustomResponse<>(Collections.singletonList(updatedWishListDTO), "ok", false, 200), HttpStatus.OK);

    }
}
