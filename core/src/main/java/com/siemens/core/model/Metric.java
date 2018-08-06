package com.siemens.core.model;


import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "metrics")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Metric extends BaseEntity<Integer> {

    @ManyToOne
    @JoinColumn(name = "holdingrecordId")
    private HoldingRecord holdingRecord;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "metric")
    private Set<YearData> yearData;



}
