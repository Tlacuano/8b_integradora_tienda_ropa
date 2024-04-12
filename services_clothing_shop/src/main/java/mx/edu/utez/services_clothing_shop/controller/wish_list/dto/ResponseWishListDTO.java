package mx.edu.utez.services_clothing_shop.controller.wish_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseWishListProductDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestUserByIdDTO;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;

import java.util.UUID;

@Data
public class ResponseWishListDTO {
    @NotNull
    private UUID idWish;
    private int amount;
    @NotBlank(message = "wishList.product.notnull")
    private ResponseWishListProductDTO product;
    @NotBlank(message = "wishList.user.notnull")
    private RequestUserByIdDTO user;

    public static ResponseWishListDTO fromWishList(BeanWishList wishList) {
        ResponseWishListDTO wishListDTO = new ResponseWishListDTO();
        wishListDTO.setIdWish(wishList.getIdWish());
        wishListDTO.setProduct(ResponseWishListProductDTO.fromProduct(wishList.getProduct()));
        wishListDTO.setUser(RequestUserByIdDTO.fromUser(wishList.getUser()));
        return wishListDTO;
    }
}
