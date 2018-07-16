package com.siemens.core.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "currency")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Currency extends BaseEntity<Long> {
    @Column(name = "name")
    private String name;

    @Column(name = "symbol")
    private String symbol;

    @OneToMany(mappedBy = "currency")
    private Set<Company> companies;
}
