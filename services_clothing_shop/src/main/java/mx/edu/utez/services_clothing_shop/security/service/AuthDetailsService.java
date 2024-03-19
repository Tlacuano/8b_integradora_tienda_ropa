package mx.edu.utez.services_clothing_shop.security.service;

import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.security.model.AuthDetails;
import mx.edu.utez.services_clothing_shop.service.user.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthDetailsService implements UserDetailsService {
    private final UserService userService;

    public AuthDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        BeanUser user = userService.getByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("El usuario con el correo %s no existe", username));
        }
        return new AuthDetails(user);
    }
}
