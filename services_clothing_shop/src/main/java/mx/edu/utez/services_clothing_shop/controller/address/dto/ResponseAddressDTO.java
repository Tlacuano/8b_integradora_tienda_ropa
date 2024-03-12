package mx.edu.utez.services_clothing_shop.controller.address.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class ResponseAddressDTO {
    private UUID idAddress;
    private String address;
    private String referencesAddress;
    private String postalCode;
    private String state;
    private String street;
    private String neighborhood;
    private UUID userID;
    private UUID statusID;
    private List<UUID> orderIDs;
}
