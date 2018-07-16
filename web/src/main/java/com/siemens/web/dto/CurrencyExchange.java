package com.siemens.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CurrencyExchange extends BaseDTO<Long> implements Serializable {

    private CurrencyDTO currency1;
    private CurrencyDTO currency2;
    private int factor;
    private DateTime dateOfExchange;

    @Override
    public String toString() {
        return "CurrencyExchange{" +
                "currency1=" + currency1 +
                ", currency2=" + currency2 +
                ", factor=" + factor +
                ", dateOfExchange=" + dateOfExchange +
                '}';
    }
}
