package mx.edu.utez.services_clothing_shop.controller.person.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ResponseGetPersonalInformationDTO {
    private String name;
    private String lastName;
    private String secondLastName;
    private String phoneNumber;
    private String gender;
    private LocalDate birthday;

    public static ResponseGetPersonalInformationDTO fromPerson(BeanPerson person) {
        ResponseGetPersonalInformationDTO response = new ResponseGetPersonalInformationDTO();
        response.setName(person.getName());
        response.setLastName(person.getLastName());
        response.setSecondLastName(person.getSecondLastName());
        response.setPhoneNumber(person.getPhoneNumber());
        response.setGender(String.valueOf(person.getGender()));
        response.setBirthday(person.getBirthday());
        return response;
    }

}
