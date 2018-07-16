package com.siemens.web.data_transfer_object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyDTO extends BaseDTO<Long> implements Serializable {

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
