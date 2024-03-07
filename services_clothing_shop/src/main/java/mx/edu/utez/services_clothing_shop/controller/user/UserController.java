package mx.edu.utez.services_clothing_shop.controller.user;

import jakarta.validation.Valid;
import mx.edu.utez.services_clothing_shop.controller.user.dto.RequestPostAccountDTO;
import mx.edu.utez.services_clothing_shop.model.user.projections.IGetPageUsers;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
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
    @Autowired
    private UserService userService;

    @GetMapping("/get-page")
    ResponseEntity<Page<IGetPageUsers>> getPageUsers(Pageable pageable){
        return new ResponseEntity<>(
                userService.getPageUsers(pageable),
                HttpStatus.OK
        );
    }

    @PostMapping("/saveAccount")
    public String saveAccount(@Valid @RequestBody RequestPostAccountDTO user){
        return null;
    }
}
