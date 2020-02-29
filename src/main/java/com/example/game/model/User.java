package com.example.game.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

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
}
