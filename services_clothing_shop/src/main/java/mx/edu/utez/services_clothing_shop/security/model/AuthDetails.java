package mx.edu.utez.services_clothing_shop.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mx.edu.utez.services_clothing_shop.model.user.BeanUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthDetails implements UserDetails {
    private String email;
    private String password;
    private boolean status;
    private List<String> role;
    private boolean emailVerified;
    private boolean privacyPolicy;
    private boolean verificationPhone;

    public AuthDetails(BeanUser user) {
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.status = user.isStatus();
        this.role = user.getRoles().stream().map(roleElement -> roleElement.getRole().getRoleName()).toList();
        this.emailVerified = user.isEmailVerified();
        this.privacyPolicy = user.isPrivacyPolicy();
        this.verificationPhone = user.getPerson() != null && user.getPerson().isVerificationPhone();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new java.util.ArrayList<>();
        for (String roleElement : role) {
            authorities.add(new SimpleGrantedAuthority(roleElement));
        }
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status;
    }
}
