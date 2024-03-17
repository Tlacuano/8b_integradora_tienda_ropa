package mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
public class RequestDataChangeIdDTO {
    private UUID requestId;
    private Map<String, Object> newUserInformation;
    private String rejectionReason;
    private String userEmail;
    private String status;



    public UUID getRequestId() {
        return requestId;
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public Map<String, Object> getNewUserInformation() {
        return newUserInformation;
    }

    public void setNewUserInformation(Map<String, Object> newUserInformation) {
        this.newUserInformation = newUserInformation;
    }

    public String getRejectionReason() {
        return rejectionReason;
    }

    public void setRejectionReason(String rejectionReason) {
        this.rejectionReason = rejectionReason;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }




}
