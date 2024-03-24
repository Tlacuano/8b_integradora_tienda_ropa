package mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto;

import lombok.Data;

@Data
public class RequestsBecomeSellerPostDTO {
    private String email;
    private UserSellerInformation userSellerInformation;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserSellerInformation getUserSellerInformation() {
        return userSellerInformation;
    }



}
