package ru.itmentor.spring.boot_security.demo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmentor.spring.boot_security.demo.models.Role;

import java.util.Optional;

@Repository
public interface RoleRepositories extends JpaRepository<Role,Integer> {
    Optional<Role> findByRole(String role);
}
