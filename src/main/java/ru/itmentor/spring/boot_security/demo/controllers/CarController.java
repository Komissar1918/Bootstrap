package ru.itmentor.spring.boot_security.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.exception.CarIncorectedData;
import ru.itmentor.spring.boot_security.demo.exception.NoSuchException;
import ru.itmentor.spring.boot_security.demo.models.Car;
import ru.itmentor.spring.boot_security.demo.models.Person;
import ru.itmentor.spring.boot_security.demo.redirect.UserAdminRedirect;
import ru.itmentor.spring.boot_security.demo.service.CarService;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
public class CarController {

    private final CarService carServiceList;


    @Autowired
    public CarController(CarService carServiceList) {
        this.carServiceList = carServiceList;

    }

    @GetMapping("/admin/all")
    public List<Car> getAllCarsAdmin(){
        return carServiceList.getAll();
    }
    @GetMapping("/user/all")
    public List<Car> getAllCarsUser(){
        return carServiceList.getAll();
    }

    @GetMapping("/admin/{id}")
    public Car show(@PathVariable("id") int id) {
        Car car = carServiceList.getById(id);
        if(car==null){
            throw new NoSuchException("There is no car with id = " + id + " int database");
        }
        return car;
    }

    @PostMapping("admin/save")
    public Car create(@RequestBody @Valid Car car, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors) {
                errorMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new NoSuchException(errorMsg.toString());
        }
        carServiceList.save(car);
        return car;
    }

    @PatchMapping("admin/edit/{id}")
    public Car update(@RequestBody @Valid Car car,
                         @PathVariable("id") int id,BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            StringBuilder errorMsg = new StringBuilder();
            throw new NoSuchException(errorMsg.toString());
        }
        if(carServiceList.getById(id).getClass().equals(Car.class)) {
            carServiceList.update(id, car);
        } else {
            StringBuilder errorMsg = new StringBuilder();
            throw new NoSuchException(errorMsg.toString());
        }
        carServiceList.update(id, car);
        return car;
    }

    @DeleteMapping("admin/delete/{id}")
    public void delete(@PathVariable("id") int id) {
        carServiceList.delete(id);
    }
}
