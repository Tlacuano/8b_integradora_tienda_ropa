package mx.edu.utez.services_clothing_shop.controller.product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseProductDTO {
    private UUID idProduct;
    private String productName;
    private String description;
    private double price;
    private int amount;
    private String subcategory;
    private String category;
    private boolean status;
    private List<ProductImageDTO> productGallery;

    public static ResponseProductDTO toProductDTO(BeanProduct product) {
        ResponseProductDTO dto = new ResponseProductDTO();
        dto.setIdProduct(product.getIdProduct());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAmount(product.getAmount());
        dto.setSubcategory(product.getSubcategory().getSubcategory());
        dto.setCategory(product.getSubcategory().getCategory().getCategory());
        dto.setStatus(product.isStatus());
        List<ProductImageDTO> productImages = new ArrayList<>();
        for (BeanProductGallery gallery : product.getProductGallery()) {
            productImages.add(new ProductImageDTO(gallery.getIdImage(), gallery.getImage(), gallery.getStatus().getStatus()));
        }
        dto.setProductGallery(productImages);
        return dto;
    }
}
