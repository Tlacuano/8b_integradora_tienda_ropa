package mx.edu.utez.services_clothing_shop.controller.address.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponseGetAddressesByEmailDTO {
    private UUID idAddress;
    private String address;
    private String referencesAddress;
    private String postalCode;
    private String state;
    private String street;
    private String status;
    private String neighborhood;

}
