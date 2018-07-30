package com.siemens.core.service;

import com.siemens.core.api.CurrencyApi;
import com.siemens.core.model.Currency;
import com.siemens.core.model.CurrencyExchange;
import com.siemens.core.repository.CurrencyExchangeRepository;
import com.siemens.core.repository.CurrencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
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

    @Transactional
    @Override
    public void addCurrency()
    {
        Map<String , Double> values = CurrencyApi.Interogate();
        for(Map.Entry<String, Double> entry : values.entrySet()){
           currencyRepository.save(
                   Currency.builder()
                   .name(entry.getKey())
                   .symbol(entry.getKey())
                   .build()
           );
        }
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
