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
    private  double PE;
    private int dividendYield;
    private Set<HoldingRecordDTO> holdingRecords;
    private SharePriceDTO sharePrice;
    private Set<CompanyGroupDTO> companyGroups;
    private CurrencyDTO currency;

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "name='" + name + '\'' +
                ", PE=" + PE +
                ", dividendYield=" + dividendYield +
                ", holdingRecords=" + holdingRecords +
                ", shareprice=" + sharePrice +
                ", companyGroups=" + companyGroups +
                ", currency=" + currency +
                '}';
    }
}
