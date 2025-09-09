// 代码生成时间: 2025-09-10 04:50:06
package com.example.model;

import io.micronaut.core.annotation.Introspected;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * User entity for data modeling
 *
 * @author Your Name
 */
@Introspected
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 50)
    private String name;

    @NotNull
    @Size(min = 5, max = 100)
    private String email;

    // Default constructor
    public User() {
    }

    // Constructor with all fields
    public User(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * toString method to display user information
     *
     * @return a string representation of the user
     */
    @Override
    public String toString() {
        return "User{"id":"" + id + "", "name":"" + name + "", "email":"" + email + ""}";
    }
}
