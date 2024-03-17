package mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto;

public class RequestChangeInformationRequest {
    private String email;
    private NewInformation newInformation;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public NewInformation getNewInformation() {
        return newInformation;
    }

}
