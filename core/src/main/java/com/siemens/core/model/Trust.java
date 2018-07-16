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
@Builder
public class Trust extends Fund {

    @Column(name="gearing")
    private int gearing;
    @Column(name="premiumDiscount")
    private int premiumDiscount;

}
