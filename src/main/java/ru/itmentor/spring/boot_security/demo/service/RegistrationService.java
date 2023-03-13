package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.models.Person;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.repositories.PersonRepositories;

import java.util.Collections;

@Service
@Transactional
public class RegistrationService {
    private final PersonRepositories personRepositories;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public RegistrationService(PersonRepositories personRepositories, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.personRepositories = personRepositories;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }
    public boolean register(Person person){
        Role role=roleService.getByName("ROLE_USER");
        Person personFromDB = personRepositories.findByEmail(person.getEmail());
        if (personFromDB != null) {
            return false;
        }
        person.setRoles(Collections.singleton(role));
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        personRepositories.save(person);
        return true;
    }
    public boolean deleteUser(int userId) {
        if (personRepositories.findById(userId).isPresent()) {
            personRepositories.deleteById(userId);
            return true;
        }
        return false;
    }
}
