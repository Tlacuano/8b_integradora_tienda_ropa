package mx.edu.utez.services_clothing_shop.controller.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.controller.order.dto.ResponseOrderDTO;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseShoppingCartProductDTO {
    private UUID idProduct;
    private String productName;
    private String description;
    private double price;
    private String category;
    private String subcategory;
    private List<BeanProductGallery> gallery;
    private int amount;

    public static ResponseShoppingCartProductDTO fromProduct(BeanProduct product) {
        ResponseShoppingCartProductDTO productDTO = new ResponseShoppingCartProductDTO();
        productDTO.setIdProduct(product.getIdProduct());
        productDTO.setProductName(product.getProductName());
        productDTO.setDescription(product.getDescription());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getSubcategory().getCategory().getCategory());
        productDTO.setSubcategory(product.getSubcategory().getSubcategory());
        productDTO.setGallery(product.getProductGallery());
        productDTO.setAmount(product.getAmount());
        return productDTO;
    }
}
