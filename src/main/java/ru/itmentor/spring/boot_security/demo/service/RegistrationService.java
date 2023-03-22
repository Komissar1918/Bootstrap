package ru.itmentor.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.models.Person;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.repositories.PersonRepositories;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepositories;

import javax.annotation.PostConstruct;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class RegistrationService {
    private final PersonRepositories personRepositories;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final RoleRepositories roleRepositories;

    @Autowired
    public RegistrationService(PersonRepositories personRepositories, PasswordEncoder passwordEncoder, RoleService roleService, RoleRepositories roleRepositories) {
        this.personRepositories = personRepositories;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.roleRepositories = roleRepositories;
    }

    public boolean register(Person person) {
        Role role = roleService.getByName("ROLE_USER");
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

    public Person passwordCoder(Person person) {
        person.setPassword(passwordEncoder.encode(person.getPassword()));
        return person;
    }

    public void save(Person person) {
        personRepositories.save(passwordCoder(person));
    }

    @PostConstruct
    public void addDefaultUser() {
        Set<Role> role1 = new HashSet<>();
        role1.add(roleRepositories.findById(1).orElse(null));
        Set<Role> role2 = new HashSet<>();
        role2.add(roleRepositories.findById(2).orElse(null));
        Person personAdmin = new Person("admin@mail.ru", "Yosyf", "Akhmetov", "admin", role1);
        Person personUser = new Person("user@mail.ru", "Olga", "Akhmetova", "user", role2);
        Person personUser2 = new Person("user2@mail.ru", "Alexandr", "Savelyev", "user", role2);
        save(personAdmin);
        save(personUser);
        save(personUser2);
    }
}
