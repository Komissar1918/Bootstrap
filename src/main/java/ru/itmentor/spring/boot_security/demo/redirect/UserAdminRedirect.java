package ru.itmentor.spring.boot_security.demo.redirect;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

public class UserAdminRedirect {
    private String userAdminRedirect(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Optional<? extends GrantedAuthority> roleAdmin = authorities.stream()
                .filter(a -> Objects.equals("ROLE_ADMIN", a.getAuthority()))
                .findAny();
        if (roleAdmin.isPresent()) return "redirect:/adminPage";
        else return "redirect:/login";
    }
}
