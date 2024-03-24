package mx.edu.utez.services_clothing_shop.controller.wish_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseWishListProductDTO;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;

import java.util.UUID;

@Data
public class ResponseInformationWishListDTO {
    @NotNull
    private UUID idWish;
    @NotBlank(message = "wishList.amount.notnull")
    private int amount;
    @NotBlank(message = "wishList.product.notnull")
    private ResponseWishListProductDTO product;

    public static ResponseInformationWishListDTO fromWish(BeanWishList wishList) {
        ResponseInformationWishListDTO wishListDTO = new ResponseInformationWishListDTO();
        wishListDTO.setIdWish(wishList.getIdWish());
        wishListDTO.setAmount(wishList.getAmount());
        wishListDTO.setProduct(ResponseWishListProductDTO.fromProduct(wishList.getProduct()));
        return wishListDTO;
    }
}
