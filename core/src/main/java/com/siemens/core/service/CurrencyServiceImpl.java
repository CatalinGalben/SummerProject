package com.siemens.core.service;

import com.siemens.core.api.CurrencyApi;
import com.siemens.core.model.Currency;
import com.siemens.core.model.CurrencyExchange;
import com.siemens.core.repository.CurrencyExchangeRepository;
import com.siemens.core.repository.CurrencyRepository;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.Optional;

@Service
public class CurrencyServiceImpl implements CurrencyServiceInterface{
    private static final Logger log = LoggerFactory.getLogger(CurrencyServiceImpl.class);
    @Autowired
    private CurrencyRepository currencyRepository;
    @Autowired
    private CurrencyExchangeRepository currencyExchangeRepository;

    private static DateTimeFormatter f = DateTimeFormat.forPattern("yyyy/MM/dd");
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
    @Transactional
    @Override
    public void setExchangeRates()
    {
        Currency currency1 = currencyRepository.findAll()
                .stream().filter(currency -> currency.getSymbol().equals("EUR")).findFirst().get();
        Optional<CurrencyExchange> currencyExchange = currencyExchangeRepository.findAll()
                .stream()
                .filter(ce -> ce.getCurrency1().getSymbol().equals("EUR") && ce.getCurrency2().getSymbol().equals("EUR")).findFirst();
        if(currencyExchange.isPresent())
            if(!f.parseDateTime(currencyExchange.get().getDateOfExchange()).isBefore(f.parseDateTime(DateTime.now().toString("yyyy/MM/dd"))))
            {
                log.trace("SORRY MATE , ALREADY ADDED CURRENCIES TODAY!");
                return;
            }
        Map<String, Double> exchangeValues = CurrencyApi.Interogate();
        int iterationContor = 0; //ONLY DURING THE FIRST ITERATION WILL
        //THE DATABASE BE SEARCHED FOR THE SYMBOLS AND DATE CHECKED, IF THIS HAPPENS DURING THE SAME DAY
        //THEN, NO UPDATE WILL OCCUR IN THE DATABASE
        //THE UPDATE OF A CURRENCY WILL OCCUR ONLY IN THE FIRST LOGIN OF A NEW DAY
        for(Map.Entry<String, Double> entry: exchangeValues.entrySet())
        {
//            if(iterationContor == 0)
//            {
//                Optional<CurrencyExchange> optionalCurrencyExchange =
//                        currencyExchangeRepository.findAll()
//                                .stream().filter(
//                                exchange -> exchange.getCurrency1().getName().equals("EUR") &&
//                                        exchange.getCurrency2().getName().equals(entry.getKey())
//                        ).findFirst();
//                if (optionalCurrencyExchange.isPresent())
//
//                    //if the dateTime of the first found currency exchange is NOT before
//                    //the current dateTime then the function shall exit and no update will be made
//                    if(!f.parseDateTime(optionalCurrencyExchange.get().getDateOfExchange()).isBefore(f.parseDateTime(DateTime.now().toString("yyyy/MM/dd"))))
//                    {
//                        log.trace("SORRY MATE, ALREADY UPDATED THE DATABASE TODAY !!!! #########################");
//                        return;
//                    }
//                else
//                    {
//                        currencyExchangeRepository.save(
//                                CurrencyExchange.builder()
//                                .currency1(currency1)
//                                .currency2(
//                                        currencyRepository.findAll()
//                                        .stream().filter(currency -> currency.getSymbol().equals(entry.getKey()))
//                                        .findFirst().get()
//                                )
//                                .dateOfExchange(DateTime.now().toString("yyyy/MM/dd"))
//                                .factor(entry.getValue())
//                                .build()
//                        );
//                        iterationContor = iterationContor + 1;
//                        continue;
//                    }
//
//            }
//            Optional<CurrencyExchange> optionalCurrencyExchange =
//                    currencyExchangeRepository.findAll().stream()
//                    .filter(currencyExchange -> currencyExchange.getCurrency1().getSymbol().equals("EUR") && currencyExchange.getCurrency2().getSymbol().equals(entry.getKey()))
//                    .findFirst();
//            if(!optionalCurrencyExchange.isPresent())
//            {
//                currencyExchangeRepository.save(
//                        CurrencyExchange.builder()
//                        .currency1(currency1)
//                        .currency2(
//                                currencyRepository.findAll()
//                                        .stream().filter(currency -> currency.getSymbol().equals(entry.getKey()))
//                                        .findFirst().get()
//                        )
//                        .dateOfExchange(DateTime.now().toString("yyyy/MM/dd"))
//                        .factor(entry.getValue())
//                        .build()
//                );
//            }
//            else
//            {
//                optionalCurrencyExchange.get().setDateOfExchange(DateTime.now().toString("yyyy/MM/dd"));
//                optionalCurrencyExchange.get().setFactor(entry.getValue());
//                currencyExchangeRepository.save(optionalCurrencyExchange.get());
//            }

            currencyExchangeRepository.save(
                    CurrencyExchange.builder()
                                .currency1(currency1)
                                .currency2(
                                        currencyRepository.findAll()
                                        .stream().filter(currency -> currency.getSymbol().equals(entry.getKey()))
                                        .findFirst().get()
                                )
                                .dateOfExchange(DateTime.now().toString("yyyy/MM/dd"))
                                .factor(entry.getValue())
                                .build()

            );


        }
    }
    private Double convert(String currentSymbol,String desiredSymbol, Double value)
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
            convertedValue = value * currencyExchange.getFactor();
            return  convertedValue;
        }
        else {
            currencyExchange = currencyExchangeRepository
                    .findAll().stream().filter(ce ->
                            ce.getCurrency1().getSymbol().equals(desiredSymbol) &&
                                    ce.getCurrency2().getSymbol().equals(currentSymbol)
                    ).findFirst().get();
            convertedValue = value * 1/currencyExchange.getFactor();
            return  convertedValue;
        }
    }
    @Override
    public String chooseCurrency(String currentSymbol, String desiredSymbol, Double value)
    {
        if(!currentSymbol.equals("EUR") && !desiredSymbol.equals("EUR"))
        {
            Double firstValue = convert(currentSymbol, "EUR", value);
            return convert("EUR", desiredSymbol, firstValue).toString();
        }
        else
            return convert(currentSymbol, desiredSymbol, value).toString();

    }
}
