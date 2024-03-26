package mx.edu.utez.services_clothing_shop.controller.requests_data_change.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.person.GenderEnum;
import mx.edu.utez.services_clothing_shop.model.request_data_change.BeanRequestDataChange;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDataChangePutDTO {
    private UUID idRequestDataChange;
    private String rejectionReason;
    private String status;
    private String name;
    private String lastName;
    private String secondLastName;
    private String picture;
    private LocalDate birthday;
    private String phoneNumber;
    private GenderEnum gender;

}
