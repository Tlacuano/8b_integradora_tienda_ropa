package mx.edu.utez.services_clothing_shop.controller.requests_sell_product.dto;

import java.util.UUID;

public class RequestDetailsDTO {
    private UUID idRequestSellProduct;
    private String userEmail;
    private double price;
    private String description;
    private String productName;
    private String image;

    private UUID productId;

    // Constructor, getters y setters

    public RequestDetailsDTO() {
    }

    public UUID getIdRequestSellProduct() {
        return idRequestSellProduct;
    }

    public void setIdRequestSellProduct(UUID idRequestSellProduct) {
        this.idRequestSellProduct = idRequestSellProduct;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public UUID getProductId() {
        return productId;
    }

    public void setProductId(UUID productId) {
        this.productId = productId;
    }
}
