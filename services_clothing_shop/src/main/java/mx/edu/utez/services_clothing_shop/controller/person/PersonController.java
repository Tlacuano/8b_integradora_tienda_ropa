package mx.edu.utez.services_clothing_shop.controller.person;

import mx.edu.utez.services_clothing_shop.controller.person.dto.RequestPutPersonalInformationDTO;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.person.PersonService;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.validations.ValidatesFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/venta-ropa/api/person")
@CrossOrigin(origins = {"*"})
public class PersonController {
    private final UserService userService;
    private final PersonService personService;

    public PersonController(UserService userService, PersonService personService) {
        this.userService = userService;
        this.personService = personService;
    }


    @PostMapping("/post-personal-information")
    public ResponseEntity<Object> postPersonalInformation(@Validated @RequestBody RequestPutPersonalInformationDTO payload){
        BeanUser user = userService.getByEmail(payload.getEmail());

        if(user == null){
            throw new RuntimeException("user.email.exists");
        }

        BeanPerson newPersonalInformation = new BeanPerson();
        newPersonalInformation.setIdPerson(user.getIdUser());
        newPersonalInformation.setName(payload.getName());
        newPersonalInformation.setLastName(payload.getLastName());
        newPersonalInformation.setSecondLastName(payload.getSecondLastName());
        newPersonalInformation.setPhoneNumber(payload.getPhoneNumber());
        newPersonalInformation.setPicture(payload.getPicture());
        newPersonalInformation.setGender(payload.getGender());
        if(!ValidatesFunctions.isAdult(payload.getBirthday())){
            throw new RuntimeException("person.birthday.age");
        }
        newPersonalInformation.setBirthday(payload.getBirthday());
        newPersonalInformation.setUser(user);

        return new ResponseEntity<>(
                new CustomResponse<>(personService.postPersonalInformation(newPersonalInformation), "Información personal guardada", false, 200),
                HttpStatus.OK
        );
    }
}
