package ru.itmentor.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.models.Car;
import ru.itmentor.spring.boot_security.demo.service.CarService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Controller
@RequestMapping("/cars")
public class CarController {

    private final CarService carServiceList;

    @Autowired
    public CarController(CarService carServiceList) {
        this.carServiceList = carServiceList;
    }


    @GetMapping("/{id}")
    public String show(@PathVariable("id") int id, Model model) {  // получим машину по id
        model.addAttribute("car", carServiceList.getById(id));
        return "show";
    }

    @GetMapping("/new")
    public String newCar(Model model) {
        model.addAttribute("car", new Car());
        return "new";
    }

    @PostMapping()
    public String create(@ModelAttribute("car") @Valid Car car, //добавляем новую машину
                         BindingResult bindingResult,
                         Authentication auth) {
        if (bindingResult.hasErrors())               //проверяем на валидацию
            return "new";
        carServiceList.save(car);
        return userAdminRedirect(auth); //при добавлении возвращает нас на главную страницу с списком
    }

    @GetMapping("/{id}/edit")
    public String edit(Model model, @PathVariable("id") int id) {
        model.addAttribute("car", carServiceList.getById(id));
        return "edit";
    }

    @PatchMapping("/{id}")
    public String update(@ModelAttribute("car") @Valid Car car, //изменяем данные по машине
                         BindingResult bindingResult,
                         @PathVariable("id") int id,
                         Authentication auth) {
        if (bindingResult.hasErrors())     //проверяем на валидацию
            return "edit";
        carServiceList.update(id, car);
        return userAdminRedirect(auth);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") int id, Authentication auth) { //удаляем машину
        carServiceList.delete(id);
        return userAdminRedirect(auth);
    }

    private String userAdminRedirect(Authentication auth) {
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Optional<? extends GrantedAuthority> roleAdmin = authorities.stream()
                .filter(a -> Objects.equals("ROLE_ADMIN", a.getAuthority()))
                .findAny();
        if (roleAdmin.isPresent()) return "redirect:/admin";
        else return "redirect:/user";
    }
}
