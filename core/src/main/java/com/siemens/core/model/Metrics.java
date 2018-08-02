package com.siemens.core.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "metrics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Metrics extends BaseEntity<Integer> {

    @ManyToOne
    @JoinColumn(name = "holdingrecordid")
    private HoldingRecord holdingRecord;

    @Column(name = "name")
    private String name;

    //TODO
}
