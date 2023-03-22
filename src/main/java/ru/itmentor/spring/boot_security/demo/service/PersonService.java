package ru.itmentor.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.itmentor.spring.boot_security.demo.models.Person;
import ru.itmentor.spring.boot_security.demo.models.Role;
import ru.itmentor.spring.boot_security.demo.repositories.PersonRepositories;
import ru.itmentor.spring.boot_security.demo.repositories.RoleRepositories;

import javax.annotation.PostConstruct;
import java.util.*;


@Service
@Transactional(readOnly = true)
public class PersonService implements UserDetailsService {
    private final PersonRepositories personRepositories;
    private final RoleRepositories roleRepositories;


    @Autowired
    public PersonService(PersonRepositories personRepositories,
                         RoleRepositories roleRepositories) {
        this.personRepositories = personRepositories;
        this.roleRepositories = roleRepositories;

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Person person = personRepositories.findByEmail(email);
        if (person == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return person;
    }



}
