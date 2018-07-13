package com.siemens.core.model;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name="holding_record")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HoldingRecord extends BaseEntity<Long>{

    @Id
    @Column(name="hid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int hid;

    @ManyToOne
    @JoinColumn(name="pid", nullable=false)
    private Portfolio portfolio;

    @ManyToOne
    @JoinColumn(name="bid", nullable=false)
    private Broker broker;

    @OneToMany(mappedBy="holding_record")
    private Set<Fund> funds;

    @Column(name="pricePaid")
    private double pricePaid;

    @Column(name="noShares")
    private int noShares;

}
