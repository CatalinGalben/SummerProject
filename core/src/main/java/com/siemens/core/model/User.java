package com.siemens.core.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.util.Set;

@Entity
@Table(name="user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity<Long>{

    @Column(name="firstName")
    private String firstName;
    @Column(name="lastName")
    private String lastName;
    @Column(name="email")
    private String email;
    @Column(name="username")
    private String username;
    @Column(name="password")
    private String password;
    @Column(name="type")
    private String type;
    @Column(name="dob")
    private DateTime dob;
    @OneToMany(mappedBy = "user")
    private Set<HoldingRecord> holdingRecords;


    @PrePersist
    private void onCreate() {
        dob = new DateTime();
    }

    @PreUpdate
    private void onUpdate() {
        System.out.println("User:onUpdate() " + this.firstName + " " + this.lastName);

    }

}