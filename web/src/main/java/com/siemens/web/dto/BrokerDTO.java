package com.siemens.web.dto;

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
public class BrokerDTO extends BaseDTO<Long> implements Serializable {

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
