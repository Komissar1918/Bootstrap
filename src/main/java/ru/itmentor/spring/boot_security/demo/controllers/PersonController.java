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

    @GetMapping("/admin")
    public String authAdminRole(Model model,Authentication authentication) {
        model.addAttribute("authentication",authentication);

        return "admin";
    }

    @GetMapping("/user")
    public String authUserRole(Model model,Authentication authentication) {
        model.addAttribute("authentication",authentication);
        return "user";
    }

//    @RequestMapping("/userPage")
//    public String getUserPage(Model model,Authentication authentication){
//        model.addAttribute("cars", carService.getAll());
//        model.addAttribute("authentication",authentication);
//        return "users/userPage";
//    }
//
//    @RequestMapping("/adminPage")
//    public String getAdminPage(Model model, Authentication authentication ){
//        model.addAttribute("cars", carService.getAll());
//        model.addAttribute("authentication",authentication);
//        return "admin/adminPage";
//    }
}
