package com.siemens.web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupDTO extends BaseDTO {
    private String name;
    private int parentGroupID = 0;
    private Set<CompanyGroupDTO> companyGroups;

    @Override
    public String toString() {
        return "GroupDTO{" +
                "name='" + name + '\'' +
                ", parentGroupID=" + parentGroupID +
                ", companyGroups=" + companyGroups +
                '}';
    }
}
