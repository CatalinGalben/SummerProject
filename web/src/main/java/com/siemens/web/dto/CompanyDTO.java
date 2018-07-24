package com.siemens.web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyDTO extends BaseDTO{

    private String name;
    private double PE;
    private float dividendYield;
    private Set<HoldingRecordDTO> holdingRecords;
    private int sharePriceid;
    private Set<CompanyGroupDTO> companyGroups;
    private int currencyid;

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "name='" + name + '\'' +
                ", pe=" + PE +
                ", dividendYield=" + dividendYield +
                ", holdingRecords=" + holdingRecords +
                ", sharePriceid=" + sharePriceid +
                ", companyGroups=" + companyGroups +
                ", currencyid=" + currencyid +
                '}';
    }
}
