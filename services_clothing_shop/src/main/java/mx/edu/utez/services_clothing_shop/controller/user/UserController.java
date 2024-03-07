package mx.edu.utez.services_clothing_shop.controller.user;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestPostAccountDTO;
import mx.edu.utez.services_clothing_shop.model.role.BeanRole;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.projections.IGetPageUsers;
import mx.edu.utez.services_clothing_shop.service.role.RoleService;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/venta-ropa/api/user")
@CrossOrigin(origins = {"*"})
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;


    @GetMapping("/get-page")
    ResponseEntity<Page<IGetPageUsers>> getPageUsers(Pageable pageable){
        return new ResponseEntity<>(
                userService.getPageUsers(pageable),
                HttpStatus.OK
        );
    }

    @PostMapping("/saveAccount")
    public ResponseEntity<BeanUser> saveAccount(@Validated @RequestBody RequestPostAccountDTO user){
        //verificar si el correo ya existe
        if(userService.existsByEmail(user.getEmail())){
            throw new RuntimeException("user.email.exists");
        }

        BeanRole role = roleService.getRoleById(user.getRole());
        if(role == null){
            throw new RuntimeException("other");
        }

        List<BeanRole> roles = new ArrayList<>();
        roles.add(role);




        //guardar el usuario
        BeanUser toSaveUser = new BeanUser();
        toSaveUser.setEmail(user.getEmail());
        toSaveUser.setPassword(user.getPassword());



        return new ResponseEntity<>(
                userService.postAccount(toSaveUser),
                HttpStatus.OK
        );
    }
}
