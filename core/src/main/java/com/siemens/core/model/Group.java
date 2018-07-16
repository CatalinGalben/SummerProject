package com.siemens.core.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;
@Entity
@Table(name = "share_group")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Group extends BaseEntity<Integer> {
    @Column(name = "name")
    private String name;

    @Column(name = "prentGroup")
    private Group parentGroup = null;

    @OneToMany(mappedBy = "group")
    private Set<CompanyGroup> companyGroups;
}
