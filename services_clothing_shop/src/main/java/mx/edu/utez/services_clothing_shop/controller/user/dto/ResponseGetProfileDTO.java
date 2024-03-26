package mx.edu.utez.services_clothing_shop.controller.user.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;

@Data
public class ResponseGetProfileDTO {
    private String fullName;
    private String email;
    private String phone;
    private String picture;

    private boolean isAdmin;
    private boolean isSeller;
    private boolean isBuyer;

    private String taxIdentificationNumber;
    private String curp;

    public static ResponseGetProfileDTO fromUser(BeanUser user){
        ResponseGetProfileDTO userDTO = new ResponseGetProfileDTO();
        userDTO.setFullName(user.getPerson().getName() + " " + user.getPerson().getLastName() + " " + user.getPerson().getSecondLastName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhone(user.getPerson().getPhoneNumber());
        userDTO.setPicture(user.getPerson().getPicture());

        userDTO.setAdmin(user.getRoles().stream().anyMatch(role -> role.getRole().getRoleName().equals("ROLE_ADMIN")));
        userDTO.setSeller(user.getRoles().stream().anyMatch(role -> role.getRole().getRoleName().equals("ROLE_SELLER")));
        userDTO.setBuyer(user.getRoles().stream().anyMatch(role -> role.getRole().getRoleName().equals("ROLE_BUYER")));

        if(user.getPerson().getSellerInformation() == null){
            return userDTO;
        }

        userDTO.setTaxIdentificationNumber(user.getPerson().getSellerInformation().getTaxIdentificationNumber());
        userDTO.setCurp(user.getPerson().getSellerInformation().getCurp());


        return userDTO;
    }
}
