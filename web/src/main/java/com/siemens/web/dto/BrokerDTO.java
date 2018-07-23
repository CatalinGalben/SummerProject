package com.siemens.web.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrokerDTO extends BaseDTO {

    private String name;
    private double profit;
    private float dividendFee;
    private float shareFee;
    private Set<HoldingRecordDTO> holdingRecords;

    @Override
    public String toString() {
        return "BrokerDTO{" +
                "name='" + name + '\'' +
                ", profit=" + profit +
                ", dividendFee=" + dividendFee +
                ", shareFee=" + shareFee +
                ", holdingRecords=" + holdingRecords +
                '}';
    }
}
