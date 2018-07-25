package com.siemens.core.service;

import com.siemens.core.model.Currency;
import com.siemens.core.model.CurrencyExchange;
import com.siemens.core.repository.CurrencyExchangeRepository;
import com.siemens.core.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Observable;
import java.util.Optional;

@Service
public class CurrencServiceImpl implements CurrencyServiceInterface{
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    @Override
    public Currency getCurrency(Integer id)
    {
        return currencyRepository.getOne(id);
    }

    @Override
    public String chooseCurrency(String currentSymbol, String desiredSymbol, Double value)
    {
        Optional<CurrencyExchange> exchangeCase1  = currencyExchangeRepository
                .findAll().stream().filter(ce ->
                        ce.getCurrency1().getSymbol().equals(currentSymbol) &&
                                ce.getCurrency2().getSymbol().equals(desiredSymbol)
                ).findFirst();

        CurrencyExchange currencyExchange;
        Double convertedValue;
        if(exchangeCase1.isPresent())
        {
            currencyExchange = exchangeCase1.get();
            convertedValue = value * 1/currencyExchange.getFactor();
            return  convertedValue.toString();
        }
        else {
            currencyExchange = currencyExchangeRepository
                    .findAll().stream().filter(ce ->
                            ce.getCurrency1().getSymbol().equals(desiredSymbol) &&
                                    ce.getCurrency2().getSymbol().equals(currentSymbol)
                    ).findFirst().get();
            convertedValue = value * currencyExchange.getFactor();
            return  convertedValue.toString();
        }
    }
}
