package mx.edu.utez.services_clothing_shop.controller.wish_list.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseWishListProductDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.ResponseWishListUserDTO;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;

import java.util.UUID;

@Data
public class ResponseWishListDTO {
    private UUID idWishList;
    private int amount;
    private ResponseWishListUserDTO user;
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
