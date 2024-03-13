package mx.edu.utez.services_clothing_shop.controller.address.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.address.BeanAddress;
import mx.edu.utez.services_clothing_shop.model.order.BeanOrder;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    public ResponseAddressDTO() {
    }

    public static ResponseAddressDTO toAddressDTO(BeanAddress address) {
        ResponseAddressDTO dto = new ResponseAddressDTO();
        dto.setIdAddress(address.getIdAddress());
        dto.setAddress(address.getAddress());
        dto.setReferencesAddress(address.getReferencesAddress());
        dto.setPostalCode(address.getPostalCode());
        dto.setState(address.getState());
        dto.setStreet(address.getStreet());
        dto.setNeighborhood(address.getNeighborhood());
        UUID personId = address.getPerson() != null ? address.getPerson().getIdPerson(): null;
        dto.setUserID(personId);
        UUID statusId = address.getStatus() != null ? address.getStatus().getIdStatus() : null;
        dto.setStatusID(statusId);
        List<UUID> orderIds = address.getOrders().stream()
                .map(BeanOrder::getIdOrder)
                .collect(Collectors.toList());
        dto.setOrderIDs(orderIds);
        return dto;
    }

}
