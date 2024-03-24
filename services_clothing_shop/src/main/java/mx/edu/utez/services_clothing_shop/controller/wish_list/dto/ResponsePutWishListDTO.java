package mx.edu.utez.services_clothing_shop.controller.wish_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;

import java.util.UUID;
@Data
public class ResponsePutWishListDTO {
    @NotNull
    private UUID idWish;
    @NotBlank(message = "wishList.amount.notnull")
    private int amount;

    public static ResponsePutWishListDTO fromWishList(BeanWishList wishList) {
        ResponsePutWishListDTO wishListDTO = new ResponsePutWishListDTO();
        wishListDTO.setIdWish(wishList.getIdWish());
        wishListDTO.setAmount(wishList.getAmount());
        return wishListDTO;
    }
}
