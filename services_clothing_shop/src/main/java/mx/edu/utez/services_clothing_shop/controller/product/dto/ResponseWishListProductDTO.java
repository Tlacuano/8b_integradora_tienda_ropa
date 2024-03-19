package mx.edu.utez.services_clothing_shop.controller.product.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;

import java.util.UUID;

@Data
public class ResponseWishListProductDTO {
    private UUID idProduct;
    private String productName;
    private double price;
    private int amount;


    public ResponseWishListProductDTO(){

    }
    public static ResponseWishListProductDTO fromProduct (BeanProduct product){
        ResponseWishListProductDTO productDTO = new ResponseWishListProductDTO();
        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setProductName(product.getProductName());
        productDTO.setPrice(product.getPrice());
        productDTO.setAmount(product.getAmount());
        return productDTO;
    }
}
