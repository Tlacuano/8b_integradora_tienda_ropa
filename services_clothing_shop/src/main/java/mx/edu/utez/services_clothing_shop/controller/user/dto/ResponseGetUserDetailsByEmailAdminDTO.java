package mx.edu.utez.services_clothing_shop.controller.user.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ResponseGetUserDetailsByEmailAdminDTO {

    private String email;
    private boolean status;

    private UUID idPerson;
    private String name;
    private String lastName;
    private String secondLastName;
    private String picture;
    private LocalDate birthday;
    private String phoneNumber;
    private String gender;

    private String taxIdentificationNumber;
    private String secondaryPhoneNumber;
    private String imageIdentification;
    private String curp;
    private boolean blockSell;

    private List<String> roles;

    public static ResponseGetUserDetailsByEmailAdminDTO fromUser(BeanUser user) {
        ResponseGetUserDetailsByEmailAdminDTO userDetails = new ResponseGetUserDetailsByEmailAdminDTO();

        userDetails.setEmail(user.getEmail());
        userDetails.setStatus(user.isStatus());


        userDetails.setRoles(user.getRoles().stream().map(role -> role.getRole().getRoleName()).toList());

        if (user.getPerson() == null) {
            return userDetails;
        }

        userDetails.setIdPerson(user.getPerson().getIdPerson());
        userDetails.setName(user.getPerson().getName());
        userDetails.setLastName(user.getPerson().getLastName());
        userDetails.setSecondLastName(user.getPerson().getSecondLastName());
        userDetails.setPicture(user.getPerson().getPicture());
        userDetails.setBirthday(user.getPerson().getBirthday());
        userDetails.setPhoneNumber(user.getPerson().getPhoneNumber());
        userDetails.setGender(String.valueOf(user.getPerson().getGender()));

        if (user.getPerson().getSellerInformation() == null) {
            return userDetails;
        }

        userDetails.setTaxIdentificationNumber(user.getPerson().getSellerInformation().getTaxIdentificationNumber());
        userDetails.setSecondaryPhoneNumber(user.getPerson().getSellerInformation().getSecondaryPhoneNumber());
        userDetails.setImageIdentification(user.getPerson().getSellerInformation().getImageIdentification());
        userDetails.setCurp(user.getPerson().getSellerInformation().getCurp());
        userDetails.setBlockSell(user.getPerson().getSellerInformation().isBlockSell());


        return userDetails;
    }


}
