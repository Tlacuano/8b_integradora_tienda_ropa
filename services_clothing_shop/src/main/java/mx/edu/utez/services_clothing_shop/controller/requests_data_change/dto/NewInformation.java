package mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewInformation {
    private String taxIdentificationNumber;
    private String secondaryPhoneNumber;
    private String imageIdentification;
    private String curp;
}
