package com.siemens.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "currency")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Currency extends BaseEntity<Integer> {
    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "currency", cascade = CascadeType.ALL)
    private Set<Company> companies;
}
