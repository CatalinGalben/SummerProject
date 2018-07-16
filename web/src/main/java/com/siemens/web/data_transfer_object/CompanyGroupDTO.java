package com.siemens.web.data_transfer_object;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyGroupDTO extends BaseDTO<Long> implements Serializable {

    private CompanyDTO company;
    private GroupDTO group;

    @Override
    public String toString() {
        return "CompanyGroupDTO{" +
                "company=" + company +
                ", group=" + group +
                '}';
    }
}
