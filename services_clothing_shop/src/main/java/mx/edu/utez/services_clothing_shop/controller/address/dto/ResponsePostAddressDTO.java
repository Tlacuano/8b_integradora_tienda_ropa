package mx.edu.utez.services_clothing_shop.controller.address.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class ResponsePostAddressDTO {
    private UUID idAddress;
    private String address;
    private String referencesAddress;
    private String postalCode;
    private String state;
    private String street;
    private String neighborhood;
    private UUID personId;
    private UUID statusId;

    public ResponsePostAddressDTO() {
    }

    public ResponsePostAddressDTO(UUID idAddress,String address, String referencesAddress, String postalCode, String state, String street, String neighborhood, UUID personId, UUID statusId) {
        this.idAddress = idAddress;
        this.address = address;
        this.referencesAddress = referencesAddress;
        this.postalCode = postalCode;
        this.state = state;
        this.street = street;
        this.neighborhood = neighborhood;
        this.personId = personId;
        this.statusId = statusId;
    }
}
