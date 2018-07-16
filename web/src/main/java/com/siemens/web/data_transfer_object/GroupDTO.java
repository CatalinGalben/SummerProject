package com.siemens.web.data_transfer_object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDTO extends BaseDTO<Long> implements Serializable {
    private String name;
    private GroupDTO parentGroup = null;
    private Set<CompanyGroupDTO> companyGroups;

    @Override
    public String toString() {
        return "GroupDTO{" +
                "name='" + name + '\'' +
                ", parentGroup=" + parentGroup +
                ", companyGroups=" + companyGroups +
                '}';
    }
}
