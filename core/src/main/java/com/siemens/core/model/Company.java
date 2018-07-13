package com.siemens.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Table (name = "company")
@Setter
@AllArgsConstructor
@NoArgsConstructor

class Company extends BaseEntity<Long>{

    @Column(name = "sid")
    private String sid;

    @Column(name = "name")
    private String name;

    @Column(name = "PE")
    private  double PE;

    @Column(name = "divYield")
    private int divYield;

    @OneToOne
    @JoinColumn(name = "hid")
    private HoldingRecord holdingRecord;

    @OneToOne
    @JoinColumn(name = "sid")
    private Share share;

}
















































