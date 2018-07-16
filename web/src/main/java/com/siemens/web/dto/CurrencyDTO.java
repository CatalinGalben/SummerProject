package com.siemens.web.dto;

import lombok.*;

import java.io.Serializable;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CurrencyDTO extends BaseDTO<Long> implements Serializable {

    private String name;
    private String symbol;
    private Set<CompanyDTO> companies;

    @Override
    public String toString() {
        return "CurrencyDTO{" +
                "name='" + name + '\'' +
                ", symbol='" + symbol + '\'' +
                ", companies=" + companies +
                '}';
    }
}
