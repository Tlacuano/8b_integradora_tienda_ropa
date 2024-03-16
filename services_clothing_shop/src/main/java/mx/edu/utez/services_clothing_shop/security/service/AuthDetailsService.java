package mx.edu.utez.services_clothing_shop.security.service;

import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.model.user.IUser;
import mx.edu.utez.services_clothing_shop.security.controller.dtos.AuthUserDTO;
import mx.edu.utez.services_clothing_shop.security.model.AuthDetails;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
public class AuthDetailsService implements UserDetailsService {
    private final IUser repository;
    private final UserService userService;

    public AuthDetailsService(IUser repository, UserService userService) {
        this.repository = repository;
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        BeanUser user = userService.getByEmail(email);
        AuthUserDTO authUserDTO = new AuthUserDTO(user);
        return new AuthDetails(authUserDTO);
    }
}
