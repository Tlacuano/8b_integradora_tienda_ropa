package mx.edu.utez.services_clothing_shop.controller;

import mx.edu.utez.services_clothing_shop.model.user.projections.IGetPageUsers;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/venta-ropa/api/user")
@CrossOrigin(origins = {"*"})
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-page")
    Page<IGetPageUsers> getPageUsers(Pageable pageable){
        return userService.getPageUsers(pageable);
    }
}
