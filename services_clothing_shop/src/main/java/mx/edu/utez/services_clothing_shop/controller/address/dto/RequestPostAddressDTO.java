package mx.edu.utez.services_clothing_shop.controller.address.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RequestPostAddressDTO {
    private String address;
    private String referencesAddress;
    private String postalCode;
    private String state;
    private String street;
    private String neighborhood;
    private String email;
}
