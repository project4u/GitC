package com.example.game.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@MappedSuperclass
@JsonIdentityInfo(
        generator = ObjectIdGenerators.StringIdGenerator.class,
        property = "id"
)
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable implements Serializable {
    @Id
    @GeneratedValue(generator = "sequence",
    strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "sequence",
    allocationSize = 10)
    @Getter @Setter
    private Long id;

    @Column(nullable = false, updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Getter @Setter
    private Date createdAt=new Date();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Auditable)) return false;
        Auditable auditable = (Auditable) o;
        return Objects.equals(id, auditable.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @LastModifiedDate
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    @Getter @Setter
    private Date updatedAt=new Date();


}
