package mx.edu.utez.services_clothing_shop.controller.order.dto;

import lombok.Data;
import mx.edu.utez.services_clothing_shop.model.order.IOrder;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;

@Data
public class ResponseOrderDetailsDTO {
    private String orderNumber;
    private String address;
    private String street;
    private String neighborhood;
    private String referencesAddress;
    private String postalCode;
    private String state;
    private String cardNumber;

    public static ResponseOrderDetailsDTO toOrderDetailsDTO(IOrder.OrderDetailsProjection orderDetails) {
        ResponseOrderDetailsDTO responseOrderDetails = new ResponseOrderDetailsDTO();
        responseOrderDetails.setOrderNumber(orderDetails.getOrderNumber());
        responseOrderDetails.setAddress(orderDetails.getAddress());
        responseOrderDetails.setStreet(orderDetails.getStreet());
        responseOrderDetails.setNeighborhood(orderDetails.getNeighborhood());
        responseOrderDetails.setReferencesAddress(orderDetails.getReferencesAddress());
        responseOrderDetails.setPostalCode(orderDetails.getPostalCode());
        responseOrderDetails.setState(orderDetails.getState());
        responseOrderDetails.setCardNumber(EncryptionFunctions.decryptString(orderDetails.getCardNumber()));
        return responseOrderDetails;
    }
}
