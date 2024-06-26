package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPutProductDTO {
    @NotNull(message = "product.id.notnull")
    private UUID idProduct;
    @NotBlank(message = "product.name.notnull")
    @Size(min = 5, max = 30, message = "product.name.size")
    private String productName;
    @NotBlank(message = "product.description.notnull")
    @Size(min = 20, max = 255, message = "product.description.size")
    private String description;
    @DecimalMin(value = "1.0", message = "product.price.min")
    private double price;
    @Min(value = 1, message = "product.amount.min")
    private int amount;
    @NotNull(message = "product.subcategory.notnull")
    private UUID subcategory;
    @NotNull(message = "requestSellProduct.id.notnull")
    private UUID idRequestSellProduct;
    @NotEmpty(message = "product.productGallery.nonempty")
    private List<ProductImageEditDTO> productGallery;


}
