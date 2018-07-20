package com.siemens.core.model;

import javax.persistence.*;

import lombok.*;
import org.joda.time.DateTime;

import java.util.Set;

@Entity
@Table(name="portfolio_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User extends BaseEntity<Integer>{

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
    private String dob;
    @Column(name="balance")
    private double balance;
    @OneToMany(mappedBy = "user")
    private Set<HoldingRecord> holdingRecords;



    @PreUpdate
    private void onUpdate() {
        System.out.println("User:onUpdate() " + this.firstName + " " + this.lastName);

    }

}