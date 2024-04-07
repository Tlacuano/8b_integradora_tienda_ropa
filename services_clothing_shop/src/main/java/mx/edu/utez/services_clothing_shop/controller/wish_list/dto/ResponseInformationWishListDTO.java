package mx.edu.utez.services_clothing_shop.controller.wish_list.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ProductImageDTO;
import mx.edu.utez.services_clothing_shop.controller.product.dto.ResponseWishListProductDTO;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;
import mx.edu.utez.services_clothing_shop.model.wish_list.BeanWishList;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ResponseInformationWishListDTO {
    @NotNull
    private UUID idWish;
    @NotBlank(message = "wishList.amount.notnull")
    private int amount;
    @NotBlank(message = "wishList.product.notnull")
    private ResponseWishListProductDTO product;
    private List<ProductImageDTO> productGallery;

    public static ResponseInformationWishListDTO fromWish(BeanWishList wishList) {
        ResponseInformationWishListDTO wishListDTO = new ResponseInformationWishListDTO();
        wishListDTO.setIdWish(wishList.getIdWish());
        wishListDTO.setAmount(wishList.getAmount());
        wishListDTO.setProduct(ResponseWishListProductDTO.fromProduct(wishList.getProduct()));
        List<ProductImageDTO> productImages = new ArrayList<>();
        for (BeanProductGallery gallery : wishList.getProduct().getProductGallery()) {
            productImages.add(new ProductImageDTO(gallery.getIdImage(), gallery.getImage(), gallery.getStatus().getStatus()));
        }
        wishListDTO.setProductGallery(productImages);
        return wishListDTO;
    }
}
