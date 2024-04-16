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
public class ResponseProductToEditDTO {
    private UUID idProduct;
    private String productName;
    private String description;
    private double price;
    private int amount;
    private UUID subcategory;
    private String category;
    private UUID idRequestSellProduct;
    private String status;
    private List<ProductImageEditDTO> productGallery;

    public static ResponseProductToEditDTO toProductDTO(BeanProduct product) {
        ResponseProductToEditDTO dto = new ResponseProductToEditDTO();
        dto.setIdProduct(product.getIdProduct());
        dto.setProductName(product.getProductName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice());
        dto.setAmount(product.getAmount());
        dto.setSubcategory(product.getSubcategory().getIdSubcategory());
        dto.setCategory(product.getSubcategory().getCategory().getCategory());
        dto.setIdRequestSellProduct(product.getRequestSellProduct().get(0).getIdRequestSellProduct());
        dto.setStatus(product.getRequestSellProduct().get(0).getStatus().getStatus());
        List<ProductImageEditDTO> productImages = new ArrayList<>();
        for (BeanProductGallery gallery : product.getProductGallery()) {
            productImages.add(new ProductImageEditDTO(gallery.getImage(), gallery.getStatus().getStatus()));
        }
        dto.setProductGallery(productImages);
        return dto;
    }
}
