package com.siemens.web.data_transfer_object;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FundDTO {
    private String nav;
    private String ter;
    private HoldingRecordDTO holdingRecord;

    @Override
    public String toString() {
        return "FundDTO{" +
                "nav='" + nav + '\'' +
                ", ter='" + ter + '\'' +
                ", holdingRecord=" + holdingRecord +
                '}';
    }
}





























































