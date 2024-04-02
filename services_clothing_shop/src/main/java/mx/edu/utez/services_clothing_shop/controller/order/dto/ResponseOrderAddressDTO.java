package mx.edu.utez.services_clothing_shop.controller.order.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;

import java.util.UUID;

@Data
@NoArgsConstructor
public class ResponseOrderAddressDTO {
    private UUID idAddress;
    private String state;
    private String neighborhood;
    private String address;
    private String street;
    private String postalCode;
    private String references;
    private String status;

    public ResponseOrderAddressDTO toOrderAddressDTO(BeanAddress address) {
        ResponseOrderAddressDTO dto = new ResponseOrderAddressDTO();
        dto.setIdAddress(address.getIdAddress());
        dto.setState(address.getState());
        dto.setNeighborhood(address.getNeighborhood());
        dto.setAddress(address.getAddress());
        dto.setStreet(address.getStreet());
        dto.setPostalCode(address.getPostalCode());
        dto.setReferences(address.getReferencesAddress());
        dto.setStatus(address.getStatus().getStatus());
        return dto;
    }
}
