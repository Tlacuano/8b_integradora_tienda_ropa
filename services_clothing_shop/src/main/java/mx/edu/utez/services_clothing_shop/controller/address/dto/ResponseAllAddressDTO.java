package mx.edu.utez.services_clothing_shop.controller.address.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseAllAddressDTO {
    private UUID idAddress;
    private String address;
    private String referencesAddress;
    private String postalCode;
    private String state;
    private String street;
    private String neighborhood;
    private String status;

}
