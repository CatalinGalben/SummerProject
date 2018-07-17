package com.siemens.web.dto;

import lombok.*;
import org.joda.time.DateTime;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CurrencyExchangeDTO extends BaseDTO {

    private int currencyid1;
    private int currencyid2;
    private int factor;
    private DateTime dateOfExchange;

    @Override
    public String toString() {
        return "CurrencyExchangeDTO{" +
                "currencyid1=" + currencyid1 +
                ", currencyid2=" + currencyid2 +
                ", factor=" + factor +
                ", dateOfExchange=" + dateOfExchange +
                '}';
    }
}
