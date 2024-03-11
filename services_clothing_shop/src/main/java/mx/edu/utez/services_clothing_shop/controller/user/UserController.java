package mx.edu.utez.services_clothing_shop.controller.user;

import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestGetByEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestPostAccountDTO;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.person.PersonService;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import mx.edu.utez.services_clothing_shop.utils.validations.ValidatesFunctions;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/venta-ropa/api/user")
@CrossOrigin(origins = {"*"})
public class UserController {
    private final UserService userService;
    private final PersonService personService;

    public UserController(UserService userService, PersonService personService) {
        this.userService = userService;
        this.personService = personService;
    }


    @GetMapping("/get-page")
    ResponseEntity<Object> getPageUsers(Pageable pageable){
        return new ResponseEntity<>(
                new CustomResponse<>(userService.getPageUsers(pageable), "Usuarios encontrados",false, 200),
                HttpStatus.OK
        );
    }

    @PostMapping("/get-by-email")
    public ResponseEntity<Object> getByEmail(@Validated @RequestBody RequestGetByEmailDTO payload){
        return new ResponseEntity<>(
                new CustomResponse<>(userService.getByEmail(payload.getEmail()), "Usuario encontrado", false, 200),
                HttpStatus.OK
        );
    }

    @PostMapping("/post-account")
    public ResponseEntity<Object> postAccount(@Validated @RequestBody RequestPostAccountDTO user){
        //verify if the email exists
        if(userService.existsByEmail(user.getEmail())){
            throw new RuntimeException("user.email.exists");
        }

        //save user
        BeanUser newAccount = new BeanUser();
        newAccount.setEmail(user.getEmail());

        String encodedPassword = EncryptionFunctions.encryptString(user.getPassword());
        newAccount.setPassword(encodedPassword);
        newAccount.setStatus(true);

        newAccount = userService.postAccount(newAccount);

        //assign role
        userService.postRoleUser(
                user.getRole().toString(),
                newAccount.getIdUser().toString()
        );

        //organize save personal information
        BeanPerson personalInformation = new BeanPerson();
        personalInformation.setName(user.getName());
        personalInformation.setLastName(user.getLastName());
        personalInformation.setSecondLastName(user.getSecondLastName());
        personalInformation.setPhoneNumber(user.getPhoneNumber());
        personalInformation.setPicture(user.getPicture());
        personalInformation.setGender(user.getGender());
        personalInformation.setUser(newAccount);

        //verify if the birthday is valid
        if(!ValidatesFunctions.isAdult(user.getBirthday())){
            throw new RuntimeException("person.birthday.age");
        }
        personalInformation.setBirthday(user.getBirthday());
        personalInformation = personService.postPersonalInformation(personalInformation);
        
        //assign personal information
        newAccount.setPerson(personalInformation);

        return new ResponseEntity<>(
                new CustomResponse<>(true, "Cuenta registrada correctamente", false, 201),
                HttpStatus.OK
        );
    }
}
