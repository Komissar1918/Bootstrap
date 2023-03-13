package ru.itmentor.spring.boot_security.demo.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.models.Car;


@Repository
public interface CarRepositories extends JpaRepository<Car,Integer> {
}
