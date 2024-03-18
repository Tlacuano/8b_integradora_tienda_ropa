package mx.edu.utez.services_clothing_shop.controller.wish_list;

import mx.edu.utez.services_clothing_shop.controller.wish_list.dto.ResponseWishListDTO;
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
    public ResponseEntity<CustomResponse<List<ResponseWishListDTO>>> findWishListByUserEmail(@RequestBody Map<String, String> requestBody) {
        String userEmail = requestBody.get("userEmail");
        if(userEmail == null || userEmail.isEmpty()) {
            return new ResponseEntity<>(new CustomResponse<>(null, "El correo del usuario es requerido", true, 400), HttpStatus.BAD_REQUEST);
        }
        List<BeanWishList> wishLists = wishListService.findWishListByUserEmail(userEmail);
        List<ResponseWishListDTO> wishListDTOS = new ArrayList<>();
        for (BeanWishList lists : wishLists) {
            wishListDTOS.add(ResponseWishListDTO.fromWishList(lists));
        }
        if (wishListDTOS.isEmpty()) {
            return new ResponseEntity<>(new CustomResponse<>(null,"No se encontraron favoritos", true, 404), HttpStatus.NOT_FOUND);
        } else {
            return ResponseEntity.ok(new CustomResponse<>(wishListDTOS, "ok", false, 200));
        }
    }
    @PostMapping("/post-wish-list")
    public ResponseEntity<CustomResponse<List<ResponseWishListDTO>>> createWishList(@Validated @RequestBody BeanWishList wishList) {
        BeanWishList response = wishListService.saveWishList(wishList);
        if (response == null) {
            return ResponseEntity.status(new CustomResponse<>(null, "Error al guardar favoritos", true, 500).getStatus()).build();
        } else {
            ResponseWishListDTO newWishListDTO = ResponseWishListDTO.fromWishList(response);
            return ResponseEntity.ok(new CustomResponse<>(Collections.singletonList(newWishListDTO), "ok", false, 200));
        }
    }

    @DeleteMapping("/delete-wish-list/{wishListId}")
    public ResponseEntity<CustomResponse<BeanWishList>> deleteWishList(@PathVariable UUID wishListId) {
        try{
            if(wishListId != null){
                wishListService.deleteWishListById(wishListId);
                return new ResponseEntity<>(new CustomResponse<>(null, "Favorito eliminado", false, 200), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new CustomResponse<>(null, "El id del Favorito  es requerido", true, 400), HttpStatus.BAD_REQUEST);
            }
        }catch(Exception e){
            return new ResponseEntity<>(new CustomResponse<>(null, "Error al eliminar el Favorito", true, 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/put-wish-list")
    public ResponseEntity<CustomResponse<List<ResponseWishListDTO>>> updateWishList(@Validated @RequestBody BeanWishList wishList) {
        BeanWishList response = wishListService.updateWishListById(wishList);
        if (response==null) {
            return ResponseEntity.status(new CustomResponse<>(null, "Error al actualizar el favorito", true, 500).getStatus()).build();
        } else {
            ResponseWishListDTO updatedWishListDTO = ResponseWishListDTO.fromWishList(response);
            return new ResponseEntity<>(new CustomResponse<>(Collections.singletonList(updatedWishListDTO),"ok", false, 200),HttpStatus.OK);
        }
    }
}
