package com.siemens.core.model;

import lombok.*;

import javax.persistence.*;


@Entity
@Table(name="year_data")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class YearData extends BaseEntity<Integer>{

    @ManyToOne
    @JoinColumn(name = "metricId")
    private Metric metric;

    @Column(name = "year")
    private String year;

    @Column(name = "value")
    private Double value;
}
