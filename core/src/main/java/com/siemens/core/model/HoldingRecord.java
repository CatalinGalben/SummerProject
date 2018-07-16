package com.siemens.core.model;

import javax.persistence.*;

import lombok.*;

import java.util.Set;

@Entity
@Table(name="holding_record")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HoldingRecord extends BaseEntity<Long>{


    @ManyToOne
    @JoinColumn(name="userid", nullable=false)
    private User user;


    @ManyToOne
    @JoinColumn(name="brokerid", nullable=false)
    private Broker broker;


    @ManyToOne
    @JoinColumn(name = "companyid")
    private Company company;

    @OneToMany(mappedBy = "holdingRecord", cascade = CascadeType.ALL)
    private Set<Fund> funds;

    @Column(name="pricePaid")
    private double pricePaid;

    @Column(name="noShares")
    private int noShares;

}
