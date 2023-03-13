package ru.itmentor.spring.boot_security.demo.models;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Entity
@Table(name = "Car")
public class Car {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotEmpty(message = "Enter the correct value")
    @Size(min = 2,max = 20,message = "Enter the correct value")
    @Column(name = "model")
    private String model;
    @Min(value = 0, message ="Enter the correct value" )
    @Column(name = "yearOfRelease")
    private int yearOfRelease;


    public Car() {
    }

    public Car(String model, int yearOfRelease) {
        this.model = model;
        this.yearOfRelease = yearOfRelease;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    @Override
    public String toString() {
        return "Car: " + id + ", " + model + ", " + yearOfRelease;
    }
}
