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
public class HoldingRecordDTO extends BaseDTO<Long> implements Serializable {
    private UserDTO user;
    private BrokerDTO broker;
    private CompanyDTO company;
    private Set<FundDTO> funds;
    private double pricePaid;
    private int noShares;

    @Override
    public String toString() {
        return "HoldingRecordDTO{" +
                "user=" + user +
                ", broker=" + broker +
                ", company=" + company +
                ", funds=" + funds +
                ", pricePaid=" + pricePaid +
                ", noShares=" + noShares +
                '}';
    }
}
