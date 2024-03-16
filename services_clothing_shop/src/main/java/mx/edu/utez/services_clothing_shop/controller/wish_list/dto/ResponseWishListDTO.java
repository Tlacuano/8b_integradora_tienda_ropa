package mx.edu.utez.services_clothing_shop.controller.wish_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseWishListProductDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.ResponseWishListUserDTO;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;

import java.util.UUID;

@Data
public class ResponseWishListDTO {
    @NotNull
    private UUID idWishList;
    @NotBlank(message = "wishList.amount.notnull")
    private int amount;
    @NotBlank(message = "wishList.user.notFound")
    private ResponseWishListUserDTO user;
    @NotBlank(message = "wishList.product.notFound")
    private ResponseWishListProductDTO product;

    public static ResponseWishListDTO fromWishList(BeanWishList wishList) {
        ResponseWishListDTO wishListDTO = new ResponseWishListDTO();
        wishListDTO.setIdWishList(wishList.getIdWish());
        wishListDTO.setAmount(wishList.getAmount());
        wishListDTO.setUser(ResponseWishListUserDTO.fromUser(wishList.getUser()));
        wishListDTO.setProduct(ResponseWishListProductDTO.fromProduct(wishList.getProduct()));
        return wishListDTO;
    }
}
