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
public class RequestProductDTO {
    @NotBlank(message = "product.name.notnull")
    @Size(min = 5, max = 50, message = "product.name.size")
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
    @NotBlank(message = "user.email.notnull")
    private String email;
    @NotEmpty(message = "product.productGallery.nonempty")
    private List<String> productGallery;
}
