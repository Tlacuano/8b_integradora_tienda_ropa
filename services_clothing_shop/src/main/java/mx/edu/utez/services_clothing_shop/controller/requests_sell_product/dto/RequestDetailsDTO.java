package mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.product_gallery.BeanProductGallery;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetailsDTO {
    private UUID idRequestSellProduct;
    private String userEmail;
    private double price;
    private String description;
    private String productName;
    private List<BeanProductGallery> images;
    private String subcategory;
    private String category;
    private double amount;
    private UUID productId;
}
