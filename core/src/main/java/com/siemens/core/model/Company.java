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
class Company extends BaseEntity<Long>{



    @Column(name = "name")
    private String name;

    @Column(name = "PE")
    private  double PE;

    @Column(name = "dividendYield")
    private int dividendYield;

    @OneToMany(mappedBy = "company")
    private Set<HoldingRecord> holdingRecords;

    @OneToOne(mappedBy = "company")
    private SharePrice shareprice;

    @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CompanyGroup> companyGroups;


    @ManyToOne
    @JoinColumn(name = "currencyid")
    private Currency currency;

}
















































