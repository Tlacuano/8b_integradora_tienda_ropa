package mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto;

import java.util.UUID;

public class RequestsBecomeSellerDTO {
    private UUID idRequestBecomeSeller;
    private String rejectionReason;
    private String email;
    private String status;

    public RequestsBecomeSellerDTO() {
    }

    public RequestsBecomeSellerDTO(UUID idRequestBecomeSeller, String rejectionReason, String email, String status) {
        this.idRequestBecomeSeller = idRequestBecomeSeller;
        this.rejectionReason = rejectionReason;
        this.email = email;
        this.status = status;
    }

    public UUID getIdRequestBecomeSeller() {
        return idRequestBecomeSeller;
    }

    public void setIdRequestBecomeSeller(UUID idRequestBecomeSeller) {
        this.idRequestBecomeSeller = idRequestBecomeSeller;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
