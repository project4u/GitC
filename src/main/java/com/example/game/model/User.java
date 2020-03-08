package com.example.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User extends Auditable {
    @NotBlank
    @Getter @Setter
    @Email
    @Column(unique = true)
    private String email;

    @NotBlank
    @Getter @Setter
    private String saltedHashedPassword;

    @Getter @Setter
    @ManyToMany
    Set<Role> roles=new HashSet<>();

    public User(){}

    public User(User user) {
        super();
        email=user.getEmail();
        saltedHashedPassword=user.getSaltedHashedPassword();
        roles=user.getRoles();
    }
}
