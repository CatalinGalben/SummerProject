package com.siemens.web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrokerDTO extends BaseDTO {

    private String name;
    private double profit;
    private Set<HoldingRecordDTO> holdingRecords;

    @Override
    public String toString() {
        return "BrokerDTO{" +
                "name='" + name + '\'' +
                ", profit=" + profit +
                ", holdingRecords=" + holdingRecords +
                '}';
    }
}
