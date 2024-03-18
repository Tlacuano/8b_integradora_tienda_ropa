package mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDataChangePutDTO {
    private UUID idRequestDataChange;
    private String rejectionReason;
    private String status;

}
