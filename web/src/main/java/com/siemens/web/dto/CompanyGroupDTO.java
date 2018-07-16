package com.siemens.web.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyGroupDTO extends BaseDTO {

    private int companyid;
    private int groupid;

    @Override
    public String toString() {
        return "CompanyGroupDTO{" +
                "companyid=" + companyid +
                ", groupid=" + groupid +
                '}';
    }
}
