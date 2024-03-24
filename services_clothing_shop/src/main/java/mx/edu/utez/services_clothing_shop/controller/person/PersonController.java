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
    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


    @PostMapping("/post-personal-information")
    public ResponseEntity<Object> postPersonalInformation(@Validated @RequestBody RequestPutPersonalInformationDTO payload){
        return new ResponseEntity<>(
                new CustomResponse<>(personService.postPersonalInformation(payload), "Información personal registrada correctamente", false, 201),
                HttpStatus.OK);
    }
}
