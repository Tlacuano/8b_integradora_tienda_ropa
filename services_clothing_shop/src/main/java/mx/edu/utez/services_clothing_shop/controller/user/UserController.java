package mx.edu.utez.services_clothing_shop.controller.user;

import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestActionByEmailDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestPostAccountDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestCodeDTO;
import mx.edu.utez.services_clothing_shop.controller.user.dto.ResponsePageUsersDTO;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import mx.edu.utez.services_clothing_shop.utils.CustomResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/venta-ropa/api/users")
@CrossOrigin(origins = {"*"})
public class UserController {
    private final UserService userService;


    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/get-page")
    ResponseEntity<Object> getPageUsers(Pageable pageable){
        Page<ResponsePageUsersDTO> pageUsers = userService.getPageUsers(pageable);

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

    @PostMapping("/exist-by-email")
    public ResponseEntity<Object> existByEmail(@Validated @RequestBody RequestActionByEmailDTO payload){
        return new ResponseEntity<>(
                new CustomResponse<>(userService.existByEmail(payload.getEmail()), "Consulta exitosa", false, 200),
                HttpStatus.OK
        );
    }

    @PostMapping("/get-user-detaiil-by-email-admin")
    public ResponseEntity<Object> getUserDetailByEmailAdmin(@Validated @RequestBody RequestActionByEmailDTO payload){
        System.out.println(userService.getUserDetailsByEmailAdmin(payload.getEmail()));
        return new ResponseEntity<>(
                new CustomResponse<>(userService.getUserDetailsByEmailAdmin(payload.getEmail()), "Usuario encontrado", false, 200),
                HttpStatus.OK
        );
    }


    @PostMapping("/post-account")
    public ResponseEntity<Object> postAccount(@Validated @RequestBody RequestPostAccountDTO user){
        user.setRoleToAssign("ROLE_BUYER");
        return new ResponseEntity<>(
                new CustomResponse<>(userService.postAccount(user), "Cuenta registrada correctamente", false, 201),
                HttpStatus.OK
        );
    }

    @PostMapping("/post-admin-account")
    public ResponseEntity<Object> postAdminAccount(@Validated @RequestBody RequestPostAccountDTO user){
        user.setRoleToAssign("ROLE_ADMIN");
        return new ResponseEntity<>(
                new CustomResponse<>(userService.postAccount(user), "Cuenta registrada correctamente", false, 201),
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

    @PostMapping("/put-status")
    public ResponseEntity<Object> putStatus(@Validated @RequestBody RequestActionByEmailDTO payload){
        return new ResponseEntity<>(
                new CustomResponse<>(userService.changeStatusAccount(payload), "Estado de cuenta cambiado correctamente", false, 200),
                HttpStatus.OK
        );
    }

    @PostMapping("/verify-email")
    public ResponseEntity<Object> verifyCode(@Validated @RequestBody RequestCodeDTO payload){
        return new ResponseEntity<>(
                new CustomResponse<>(userService.verifyCode(payload), "Código verificado correctamente", false, 200),
                HttpStatus.OK
        );
    }

    @PostMapping("/get-profile")
    public ResponseEntity<Object> getProfile(@Validated @RequestBody RequestActionByEmailDTO payload){
        return new ResponseEntity<>(
                new CustomResponse<>(userService.getProfile(payload.getEmail()), "Perfil encontrado", false, 200),
                HttpStatus.OK
        );
    }


    @PostMapping("/resend-email-code")
    public ResponseEntity<Object> resendEmailCode(@Validated @RequestBody RequestActionByEmailDTO payload){
        return new ResponseEntity<>(
                new CustomResponse<>(userService.resendEmailCode(payload.getEmail()), "Código reenviado correctamente", false, 200),
                HttpStatus.OK
        );
    }

    @PostMapping("/delete-incomplete-account")
    public ResponseEntity<Object> deleteIncompleteAccount(@Validated @RequestBody RequestActionByEmailDTO payload){
        return new ResponseEntity<>(
                new CustomResponse<>(userService.deleteIncompleteAccount(payload.getEmail()), "Cuenta eliminada correctamente", false, 200),
                HttpStatus.OK
        );
    }

}
