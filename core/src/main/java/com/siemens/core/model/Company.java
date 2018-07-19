package com.siemens.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Entity
@Getter
@Table (name = "company")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Company extends BaseEntity<Integer>{



    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "PE")
    private  double PE;

    @Column(name = "dividendYield")
    private float dividendYield;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL)
    private Set<Fund> funds;


    @OneToMany(mappedBy = "company")
    private Set<HoldingRecord> holdingRecords;

    @OneToOne(mappedBy = "company")
    private SharePrice sharePrice;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanyGroup> companyGroups;


    @ManyToOne
    @JoinColumn(name = "currencyid")
    private Currency currency;

}
















































