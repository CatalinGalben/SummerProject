package com.siemens.core.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name = "fund")
@Getter
@Inheritance(strategy = InheritanceType.JOINED)
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(builderMethodName = "fundBuilder")
public class Fund extends BaseEntity<Integer>{

    @Column(name = "nav")
    private float nav;
    @Column(name = "ter")
    private float ter;

    public Fund(float nav, float ter) {
        this.nav = nav;
        this.ter = ter;
    }

    @ManyToOne
    @JoinColumn(name="holdingRecordId", nullable=false)
    private Company company;
}
