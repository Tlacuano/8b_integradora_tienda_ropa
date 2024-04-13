package mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.person.GenderEnum;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDataChangePutDTO {
    private UUID idRequestDataChange;
    private String rejectionReason;
    private String status;
    private String taxIdentificationNumber;
    private String secondaryPhoneNumber;
    private String imageIdentification;
    private String curp;
}
