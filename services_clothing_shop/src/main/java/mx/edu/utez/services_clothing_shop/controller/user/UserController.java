package mx.edu.utez.services_clothing_shop.controller.user;

import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestPostAccountDTO;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.projections.IGetPageUsers;
import mx.edu.utez.services_clothing_shop.service.role.RoleService;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
    private final RoleService roleService;

    public UserController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping("/get-page")
    ResponseEntity<Page<IGetPageUsers>> getPageUsers(Pageable pageable){
        return new ResponseEntity<>(
                userService.getPageUsers(pageable),
                HttpStatus.OK
        );
    }

    @PostMapping("/saveAccount")
    public ResponseEntity<BeanUser> saveAccount(@Validated @RequestBody RequestPostAccountDTO user){
        //verify if the email exists
        if(userService.existsByEmail(user.getEmail())){
            throw new RuntimeException("user.email.exists");
        }

        //save user
        BeanUser toSaveUser = new BeanUser();
        toSaveUser.setEmail(user.getEmail());

        String encodedPassword = EncryptionFunctions.encryptString(user.getPassword());
        toSaveUser.setPassword(encodedPassword);
        toSaveUser.setStatus(true);

        toSaveUser = userService.postAccount(toSaveUser);

        //assign role
        userService.postRoleUser(
                user.getRole().toString(),
                toSaveUser.getIdUser().toString()
        );

        //save personal information



        return new ResponseEntity<>(
                userService.postAccount(toSaveUser),
                HttpStatus.OK
        );
    }
}
