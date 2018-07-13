package com.siemens.core.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="trust")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Trust extends Fund {

    @Column(name="gearing")
    private int gearing;
    @Column(name="premiumDiscount")
    private int premiumDiscount;

}
