package com.siemens.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Inheritance
@Table(name="fund")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Fund {

    @Column(name="nav")
    private String nav;
    @Column(name="ter")
    private String ter;

    @ManyToOne
    @JoinColumn(name="hid", nullable=false)
    private HoldingRecord holdingRecord;
}
