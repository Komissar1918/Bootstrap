package ru.itmentor.spring.boot_security.demo.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private int id;
    @Column(name = "role")
    private String role;



    public Role() {
    }

    public Role(int id) {
        this.id = id;
    }

    public Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public void setAuthority(String role) {
        this.role=role;
    }

    @Override
    public String toString() {
        return role;
    }
}
