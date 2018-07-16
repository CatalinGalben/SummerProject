package com.siemens.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Table(name = "fund")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Fund extends BaseEntity<Long>{

    @Column(name="nav")
    private String nav;
    @Column(name="ter")
    private String ter;


    @ManyToOne
    @JoinColumn(name="holdingRecordid", nullable=false)
    private HoldingRecord holdingRecord;
}
