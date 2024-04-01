package mx.edu.utez.services_clothing_shop.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import mx.edu.utez.services_clothing_shop.security.model.AuthDetails;
import mx.edu.utez.services_clothing_shop.utils.security.EncryptionFunctions;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static mx.edu.utez.services_clothing_shop.security.config.TokenJwtConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        BeanUser user = null;
        String email = null;
        String password = null;
        try {
            user = new ObjectMapper().readValue(request.getInputStream(), BeanUser.class);
            email = user.getEmail();
            password = user.getPassword();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(email, password);
        return authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        AuthDetails user = (AuthDetails) authResult.getPrincipal();
        String email = user.getEmail();
        boolean hasMultipleRoles = user.getAuthorities().size() > 1;
        String role = "";
        for (GrantedAuthority authority : user.getAuthorities()) {
            if (authority.getAuthority().equals("ROLE_ADMIN")) {
                role = "ADMIN";
            }else if(authority.getAuthority().equals("ROLE_SUPERADMIN")){
                role = "SUPERADMIN";
            }else {
                role = "BUYER";
            }
            break;
        }
        boolean emailVerified = user.isEmailVerified();
        boolean privacyPolicy = user.isPrivacyPolicy();
        boolean verificationPhone = user.isVerificationPhone();

        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();
        Claims claims = Jwts.claims()
                .add("authorities", new ObjectMapper().writeValueAsString(roles))
                .build();

        String token = Jwts.builder()
                .subject(email)
                .claims(claims)
                .expiration(new Date(System.currentTimeMillis() + 18000000))
                .issuedAt(new Date())
                .signWith(SECRET_KEY).compact();

        response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + token);

        Map<String, Object> body = new HashMap<>();
        body.put("token", token);
        body.put("email", email);
        body.put("hasMultipleRoles", hasMultipleRoles);
        body.put("role", role);

        if(!emailVerified){
            body.put("emailVerified", false);
        }
        if(!privacyPolicy){
            body.put("privacyPolicy", false);
        }
        if(!verificationPhone){
            body.put("verificationPhone", false);
        }


        String json = JsonMapper.builder().build().writeValueAsString(body);
        String encryptedJson = EncryptionFunctions.encryptString(json);


        response.getWriter().write(new ObjectMapper().writeValueAsString(encryptedJson));
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpStatus.OK.value());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<>();
        body.put("message", "Error de autenticación: correo o contraseña incorrectos");
        body.put("error", failed.getMessage());
        body.put("status", HttpStatus.BAD_REQUEST.value());

        String json = JsonMapper.builder().build().writeValueAsString(body);
        String encryptedJson = EncryptionFunctions.encryptString(json);

        response.getWriter().write(new ObjectMapper().writeValueAsString(encryptedJson));
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        response.setContentType(CONTENT_TYPE);
    }
}
