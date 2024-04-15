package mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDataChangeIdDTO {
    private UUID requestId;
    private Map<String, Object> newUserInformation;
    private String rejectionReason;
    private String userEmail;
    private String status;
    private String taxIdentificationNumber;
    private String secondaryPhoneNumber;
    private String imageIdentification;
    private String curp;
}
