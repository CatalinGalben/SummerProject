package com.siemens.web.converter;

import com.siemens.core.model.CurrencyExchange;
import com.siemens.web.dto.CurrencyExchangeDTO;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeConverter extends AbstractConverter<CurrencyExchange, CurrencyExchangeDTO>{
    @Override
    public CurrencyExchange convertDtoToModel(CurrencyExchangeDTO currencyExchangeDTO) {
        return CurrencyExchange.builder()
                .currency1(null)
                .currency2(null)
                .dateOfExchange(currencyExchangeDTO.getDateOfExchange())
                .build();
    }

    @Override
    public CurrencyExchangeDTO convertModelToDto(CurrencyExchange currencyExchange) {
        return CurrencyExchangeDTO.builder()
                .currencyid1(currencyExchange.getCurrency1().getId())
                .currencyid2(currencyExchange.getCurrency2().getId())
                .factor(currencyExchange.getFactor())
                .dateOfExchange(currencyExchange.getDateOfExchange())
                .build();
    }
}
