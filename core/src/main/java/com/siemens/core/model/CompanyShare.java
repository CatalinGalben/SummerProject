package com.siemens.core.model;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyShare extends BaseEntity<Integer>{
    private Company company;
    private SharePrice sharePrice;
}
