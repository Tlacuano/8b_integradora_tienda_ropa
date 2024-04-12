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
        ResponseGetUserDetailsByEmailAdminDTO UserDetails = new ResponseGetUserDetailsByEmailAdminDTO();

        UserDetails.setEmail(user.getEmail());
        UserDetails.setStatus(user.isStatus());


        UserDetails.setRoles(user.getRoles().stream().map(role -> role.getRole().getRoleName()).toList());

        if(user.getPerson() == null){
            return UserDetails;
        }
        
        UserDetails.setIdPerson(user.getPerson().getIdPerson());
        UserDetails.setName(user.getPerson().getName());
        UserDetails.setLastName(user.getPerson().getLastName());
        UserDetails.setSecondLastName(user.getPerson().getSecondLastName());
        UserDetails.setPicture(user.getPerson().getPicture());
        UserDetails.setBirthday(user.getPerson().getBirthday());
        UserDetails.setPhoneNumber(user.getPerson().getPhoneNumber());
        UserDetails.setGender(String.valueOf(user.getPerson().getGender()));

        if(user.getPerson().getSellerInformation() == null){
            return UserDetails;
        }

        UserDetails.setTaxIdentificationNumber(user.getPerson().getSellerInformation().getTaxIdentificationNumber());
        UserDetails.setSecondaryPhoneNumber(user.getPerson().getSellerInformation().getSecondaryPhoneNumber());
        UserDetails.setImageIdentification(user.getPerson().getSellerInformation().getImageIdentification());
        UserDetails.setCurp(user.getPerson().getSellerInformation().getCurp());
        UserDetails.setBlockSell(user.getPerson().getSellerInformation().isBlockSell());


        return UserDetails;
    }



}
