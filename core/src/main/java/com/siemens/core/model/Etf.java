package com.siemens.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="etf")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Etf extends Fund{

    @Column(name="type")
    private int type;
}


