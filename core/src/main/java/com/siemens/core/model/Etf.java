package com.siemens.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="etf")
@Getter
@PrimaryKeyJoinColumn(name = "fundid")
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Etf extends Fund{

    @Column(name="type")
    private int type;

    @Builder(builderMethodName = "etfBuilder")
    public Etf(float nav, float ter, int type)
    {
        super(nav, ter);
        this.type = type;
    }
}


