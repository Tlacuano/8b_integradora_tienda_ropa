package mx.edu.utez.services_clothing_shop.controller.product.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class RequestProductDTO {
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
    @NotNull(message = "product.user.notnull")
    private UUID user;
    private boolean status;
    @NotEmpty(message = "product.productGallery.nonempty")
    private List<String> productGallery;

    public RequestProductDTO() {
    }

    public RequestProductDTO(String productName, String description, double price, int amount, UUID subcategory, UUID user, boolean status, List<String> productGallery) {
        this.productName = productName;
        this.description = description;
        this.price = price;
        this.amount = amount;
        this.subcategory = subcategory;
        this.user = user;
        this.status = status;
        this.productGallery = productGallery;
    }
}
