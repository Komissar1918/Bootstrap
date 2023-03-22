package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepositories;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepositories roleRepositories;

    @Autowired
    public RoleService(RoleRepositories roleRepositories) {
        this.roleRepositories = roleRepositories;
    }
    public Role getByName(String role){
        Optional<Role> roleOptional = roleRepositories.findByRole(role);
        return roleOptional.orElseThrow();
    }
    @PostConstruct
    public void addDefaultRole() {
        roleRepositories.save(new Role("ROLE_ADMIN"));
        roleRepositories.save(new Role("ROLE_USER"));
    }
}
