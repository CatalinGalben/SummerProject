package com.siemens.web.dto;

import lombok.*;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class CurrencyExchangeDTO extends BaseDTO {

    private int currencyid1;
    private int currencyid2;
    private Double factor;
    private String dateOfExchange;

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
