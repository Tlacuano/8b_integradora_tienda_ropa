package mx.edu.utez.services_clothing_shop.controller.product.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.product.BeanProduct;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ResponseProductDTO {
    private UUID idProduct;
    private String productName;
    private String description;
    private double price;
    private int amount;
    private String subcategory;
    private String category;
    private boolean status;
    private List<String> productGallery;

    public ResponseProductDTO() {
    }

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
        List<String> imageUrls = new ArrayList<>();
        for (BeanProductGallery gallery : product.getProductGallery()) {
            imageUrls.add(gallery.getImage());
        }
        dto.setProductGallery(imageUrls);
        return dto;
    }
}
