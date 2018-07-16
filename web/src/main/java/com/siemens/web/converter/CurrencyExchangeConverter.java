package com.siemens.web.converter;

import com.siemens.core.model.Currency;
import com.siemens.core.model.CurrencyExchange;
import com.siemens.web.dto.CurrencyExchangeDTO;
import org.springframework.stereotype.Component;

@Component
public class CurrencyExchangeConverter extends AbstractConverterBaseEntity<CurrencyExchange, CurrencyExchangeDTO> {

    @Override
    public CurrencyExchangeDTO convertModelToDto(CurrencyExchange currencyExchange){
        CurrencyExchangeDTO currencyExchangeDTO = CurrencyExchangeDTO.builder()
                .currencyid1(currencyExchange.getCurrency1().getId())
                .currencyid2(currencyExchange.getCurrency2().getId())
                .dateOfExchange(currencyExchange.getDateOfExchange())
                .build();
        currencyExchangeDTO.setId(currencyExchange.getId());
        return currencyExchangeDTO;
    }

    @Override
    public CurrencyExchange convertDtoToModel (CurrencyExchangeDTO currencyExchangeDTO) {
        //todo
        return null;
    }
}
