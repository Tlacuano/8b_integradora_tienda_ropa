package mx.edu.utez.services_clothing_shop.controller.requests_become_seller.dto;

public class UserSellerInformation {
    private String taxIdentificationNumber;
    private String secondaryPhoneNumber;
    private Boolean privacyPolicyAgreement;
    private String imageIdentification;
    private String curp;


    public String getTaxIdentificationNumber() {
        return taxIdentificationNumber;
    }

    public String getSecondaryPhoneNumber() {
        return secondaryPhoneNumber;
    }


    public Boolean getPrivacyPolicyAgreement() {
        return privacyPolicyAgreement;
    }



    public String getImageIdentification() {
        return imageIdentification;
    }



    public String getcurp() {
        return curp;
    }


}
