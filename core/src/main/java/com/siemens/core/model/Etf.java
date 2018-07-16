package com.siemens.core.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name="etf")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Etf extends Fund{

    @Column(name="type")
    private int type;
}


