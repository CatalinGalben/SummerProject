package com.siemens.core.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="trust")
@Getter
@Setter
@PrimaryKeyJoinColumn(name = "fundid")
@AllArgsConstructor
@NoArgsConstructor
public class Trust extends Fund {

    @Column(name="gearing")
    private float gearing;
    @Column(name="premiumDiscount")
    private float premiumDiscount;

    @Builder
    public Trust(float nav, float ter, float gearing, float premiumDiscount){
        super(nav, ter);
        this.gearing = gearing;
        this.premiumDiscount = premiumDiscount;
    }

}
