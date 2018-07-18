package com.siemens.web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HoldingRecordDTO extends BaseDTO {
    private int userid;
    private int brokerid;
    private int companyid;
    private double pricePaid;
    private int noShares;

    @Override
    public String toString() {
        return "HoldingRecordDTO{" +
                "userid=" + userid +
                ", brokerid=" + brokerid +
                ", companyid=" + companyid +
                ", pricePaid=" + pricePaid +
                ", noShares=" + noShares +
                '}';
    }
}
