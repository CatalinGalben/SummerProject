package com.siemens.core.service;

import com.siemens.core.model.Currency;
import com.siemens.core.model.CurrencyExchange;

import java.util.List;

public interface CurrencyServiceInterface {

    String chooseCurrency(String currentSymbol, String desiredSymbol, Double value);
    Currency getCurrency(Integer id);
    public void addCurrency();
    void setExchangeRates();
    List<Currency> getCurrencies();
    List<CurrencyExchange> getTodayRates();
}
