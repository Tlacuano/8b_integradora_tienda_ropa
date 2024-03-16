package mx.edu.utez.services_clothing_shop.controller.user;

import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestPostAccountDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.ResponsePageUsersDTO;
import mx.edu.utez.services_clothing_shop.model.person.BeanPerson;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.service.person.PersonService;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import mx.edu.utez.services_clothing_shop.utils.validations.ValidatesFunctions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/venta-ropa/api/user")
@CrossOrigin(origins = {"*"})
public class UserController {
    private final UserService userService;
    private final PersonService personService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, PersonService personService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.personService = personService;
        this.passwordEncoder = passwordEncoder;
    }


    @GetMapping("/get-page")
    ResponseEntity<Object> getPageUsers(Pageable pageable){
        Page<BeanUser> users = userService.getPageUsers(pageable);
        Page<ResponsePageUsersDTO> pageUsers = users.map(ResponsePageUsersDTO::fromUser);


        return new ResponseEntity<>(
                new CustomResponse<>(pageUsers, "Usuarios encontrados",false, 200),
                HttpStatus.OK
        );
    }


    @PostMapping("/get-by-email")
    public ResponseEntity<Object> getByEmail(@Validated @RequestBody RequestActionByEmailDTO payload){
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

        newAccount.setPassword(passwordEncoder.encode(user.getPassword()));
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

    @PostMapping("/delete-account")
    public ResponseEntity<Object> deleteAccount(@Validated @RequestBody RequestActionByEmailDTO payload){
        userService.deleteAccount(payload.getEmail());
        return new ResponseEntity<>(
                new CustomResponse<>(true, "Cuenta eliminada correctamente", false, 200),
                HttpStatus.OK
        );
    }

    @PostMapping("/change-status")
    public ResponseEntity<Object> changeStatus(@Validated @RequestBody RequestActionByEmailDTO payload){
        BeanUser user = userService.getByEmail(payload.getEmail());

        if(user == null){
            throw new RuntimeException("user.email.exists");
        }

        user.setStatus(!user.isStatus());

        userService.postAccount(user);

        return new ResponseEntity<>(
                new CustomResponse<>(true, "Estado de la cuenta cambiado correctamente", false, 200),
                HttpStatus.OK
        );
    }

    @PostMapping("/get-roles-by-email")
    public ResponseEntity<Object> getRolesByEmail(@Validated @RequestBody RequestActionByEmailDTO payload){
        BeanUser user = userService.getByEmail(payload.getEmail());

        if(user == null){
            throw new RuntimeException("user.email.exists");
        }

        return new ResponseEntity<>(
                new CustomResponse<>(user.getRoles(), "Roles encontrados", false, 200),
                HttpStatus.OK
        );
    }
}
