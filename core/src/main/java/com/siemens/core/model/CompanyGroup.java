package com.siemens.core.model;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "company_group")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyGroup extends BaseEntity<Long> {


    @ManyToOne
    @JoinColumn(name = "companyid")
    private Company company;


    @ManyToOne
    @JoinColumn(name = "groupid")
    private Group group;

}
