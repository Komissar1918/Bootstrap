package ru.itmentor.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.itmentor.spring.boot_security.demo.models.Person;
import ru.itmentor.spring.boot_security.demo.service.CarService;
import ru.itmentor.spring.boot_security.demo.service.PersonService;
import ru.itmentor.spring.boot_security.demo.service.RegistrationService;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Controller
public class PersonController {
    private final PersonService personService;
    private final CarService carService;
    private final RegistrationService registrationService;

    @Autowired
    public PersonController(PersonService personService, CarService carService, RegistrationService registrationService) {
        this.personService = personService;
        this.carService = carService;
        this.registrationService = registrationService;
    }
    @GetMapping(value = "login")
    public String loginPage() {
        return "users/login";
    }

    @RequestMapping("/userPage")
    public String getUserPage(Model model,Authentication authentication){
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("authentication",authentication);
        return "users/userPage";
    }
    @RequestMapping("/adminPage")
    public String getAdminPage(Model model, Authentication authentication ){
        model.addAttribute("cars", carService.getAll());
        model.addAttribute("authentication",authentication);
        return "admin/adminPage";
    }

//    @GetMapping("/registration")
//    public String registrationPage(Model model) {
//        model.addAttribute("person", new Person());
//        return "registration";
//    }
//
//    @PostMapping("/registration")
//    public String performRegistration(@ModelAttribute("person") @Valid Person person,
//                                      BindingResult bindingResult,Authentication auth) {
//        if(bindingResult.hasErrors())
//            return "registration";
//        registrationService.register(person);
//        return userAdminRedirect(auth);
//    }

    private String userAdminRedirect(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Optional<? extends GrantedAuthority> roleAdmin = authorities.stream()
                .filter(a -> Objects.equals("ROLE_ADMIN", a.getAuthority()))
                .findAny();
        if (roleAdmin.isPresent()) return "redirect:/admin";
        else return "redirect:/users";
    }
}
