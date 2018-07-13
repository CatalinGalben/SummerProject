package com.siemens.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;


@Entity
@Table(name="broker")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Broker extends BaseEntity<Long>{

    @Column(name="name")
    private String name;
    @Column(name="profit")
    private double profit;

    @OneToMany(mappedBy="broker")
    private Set<HoldingRecord> holdingRecords;
}
