package com.barreto.stockmanagement.domains.user;

import com.barreto.stockmanagement.domains.AbstractDomain;
import com.barreto.stockmanagement.infra.exceptions.BadRequestException;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

@Table(name = "users")
@Entity
@Getter
@Setter
@NoArgsConstructor
@Builder
public class User extends AbstractDomain implements UserDetails {
    private String login;
    private String password;
    private UserRole role;

    public User(String login, String password, UserRole role) {
        if (!IsValidEmail(login)) {
            throw new BadRequestException("This is a not valid email!");
        }

        this.login = login;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == UserRole.ADMIN) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() {
        return login;
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
        return true;
    }

    private boolean IsValidEmail(String emailAddress) {
        String regex = "^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

        return Pattern.matches(regex, emailAddress);
    }
}
