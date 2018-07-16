package com.siemens.core.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;
@Entity
@Table(name = "group")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Group extends BaseEntity<Long> {
    @Column(name = "name")
    private String name;

    @Column(name = "prentGroup")
    private Group parentGroup = null;

    @OneToMany(mappedBy = "group")
    private Set<CompanyGroup> companyGroups;
}
