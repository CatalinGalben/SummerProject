package com.siemens.web.converter;

import com.siemens.core.model.Currency;
import com.siemens.web.dto.CurrencyDTO;
import org.springframework.stereotype.Component;

@Component
public class CurrencyConverter extends AbstractConverterBaseEntity<Currency, CurrencyDTO> {

    @Override
    public Currency convertDtoToModel(CurrencyDTO currencyDTO){
        Currency currency = Currency.builder()
                .name(currencyDTO.getName())
                .symbol(currencyDTO.getSymbol())
                .build();
        currency.setId(currencyDTO.getId());
        return currency;
    }

    @Override
    public CurrencyDTO convertModelToDto(Currency currency){
        CurrencyDTO currencyDTO = CurrencyDTO.builder()
                .name(currency.getName())
                .symbol(currency.getSymbol())
                .build();
        currencyDTO.setId(currency.getId());
        return currencyDTO;
    }
}
