package com.example.game.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.repository.cdi.Eager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

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

    public void setSaltedHashedPassword(String password){
        this.saltedHashedPassword=new BCryptPasswordEncoder(5).encode(password);
    }

    @Getter @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    Set<Role> roles=new HashSet<>();

    public User(){}

    public User(User user) {
        super();
        email=user.getEmail();
        saltedHashedPassword=user.getSaltedHashedPassword();
        roles=user.getRoles();
    }
}
