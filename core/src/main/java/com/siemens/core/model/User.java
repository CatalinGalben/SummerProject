package com.siemens.core.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

@Entity
@Table(name="user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity<Long>{
    @Id
    @Column(name="uid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int uid;
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

    @PrePersist
    private void onCreate() {
        dob = new DateTime();
    }

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private Portfolio portfolio;

    @PreUpdate
    private void onUpdate() {
        System.out.println("User:onUpdate() " + this.firstName + " " + this.lastName);

    }

}