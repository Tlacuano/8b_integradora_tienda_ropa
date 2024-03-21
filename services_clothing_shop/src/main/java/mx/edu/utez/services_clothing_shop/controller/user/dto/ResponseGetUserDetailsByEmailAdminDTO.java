package mx.edu.utez.services_clothing_shop.controller.user.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;

import java.time.LocalDate;
import java.util.List;

@Data
public class ResponseGetUserDetailsByEmailAdminDTO {

    private String email;


    private String name;
    private String lastName;
    private String secondLastName;
    private String picture;
    private LocalDate birthday;
    private String phoneNumber;
    private String gender;

    private String taxIdentificationNumber;
    private String secondaryPhoneNumber;
    private boolean privacyPolicyAgreement;
    private String imageIdentification;
    private String curp;

    private List<String> roles;

    public static ResponseGetUserDetailsByEmailAdminDTO fromUser(BeanUser user) {
        ResponseGetUserDetailsByEmailAdminDTO UserDetails = new ResponseGetUserDetailsByEmailAdminDTO();

        UserDetails.setEmail(user.getEmail());

        UserDetails.setName(user.getPerson().getName());
        UserDetails.setLastName(user.getPerson().getLastName());
        UserDetails.setSecondLastName(user.getPerson().getSecondLastName());
        UserDetails.setPicture(user.getPerson().getPicture());
        UserDetails.setBirthday(user.getPerson().getBirthday());
        UserDetails.setPhoneNumber(user.getPerson().getPhoneNumber());
        UserDetails.setGender(String.valueOf(user.getPerson().getGender()));

        UserDetails.setTaxIdentificationNumber(user.getPerson().getSellerInformation().getTaxIdentificationNumber());
        UserDetails.setSecondaryPhoneNumber(user.getPerson().getSellerInformation().getSecondaryPhoneNumber());
        UserDetails.setPrivacyPolicyAgreement(user.getPerson().getSellerInformation().isPrivacyPolicyAgreement());
        UserDetails.setImageIdentification(user.getPerson().getSellerInformation().getImageIdentification());
        UserDetails.setCurp(user.getPerson().getSellerInformation().getCurp());

        UserDetails.setRoles(user.getRoles().stream().map(role -> role.getRole().getRoleName()).toList());

        return UserDetails;
    }



}
