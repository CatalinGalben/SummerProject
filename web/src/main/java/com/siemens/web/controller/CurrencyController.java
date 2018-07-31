package com.siemens.web.controller;

import com.siemens.core.model.Currency;
import com.siemens.core.model.CurrencyExchange;
import com.siemens.core.service.CurrencyServiceInterface;
import com.siemens.web.converter.CurrencyConverter;
import com.siemens.web.converter.CurrencyExchangeConverter;
import com.siemens.web.dto.CurrencyDTO;
import com.siemens.web.dto.CurrencyExchangeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class CurrencyController {
    private static final Logger log = LoggerFactory.getLogger(CurrencyController.class);
    @Autowired
    private CurrencyServiceInterface currencyService;
    @Autowired
    private CurrencyConverter currencyConverter;
    @Autowired
    private CurrencyExchangeConverter currencyExchangeConverter;
    @RequestMapping(value = "/currency/currencies" ,method = RequestMethod.GET)
    public Set<CurrencyDTO> getAllCurrencies()
    {
        List<Currency> currencies = currencyService.getCurrencies();
        return (Set<CurrencyDTO>) currencyConverter.convertModelsToDtos(currencies);
    }
    @RequestMapping(value = "/currency/currencyexchanges", method = RequestMethod.GET)
    public Set<CurrencyExchangeDTO> getExchanges()
    {
        List<CurrencyExchange> currencyExchanges = currencyService.getTodayRates();
        return (Set<CurrencyExchangeDTO>) currencyExchangeConverter.convertModelsToDtos(currencyExchanges);
    }
    @RequestMapping(value = "/currency/{id}", method = RequestMethod.GET)
    public CurrencyDTO getCurrency(@PathVariable final Integer id)
    {
        return currencyConverter.convertModelToDto(currencyService.getCurrency(id));
    }

    @RequestMapping(value = "/currency", method = RequestMethod.GET)
    public String changeCurrency(@RequestBody final String currSymbol,
                          @RequestBody final String desiredSymbol,
                          @RequestBody final Double value){
        log.trace("User requested change of currency from "+currSymbol+ " to "+desiredSymbol);
        return currencyService.chooseCurrency(currSymbol, desiredSymbol, value);
    }
}
