package com.siemens.core.service;

import com.siemens.core.model.Currency;

public interface CurrencyServiceInterface {

    String chooseCurrency(String currentSymbol, String desiredSymbol, Double value);
    Currency getCurrency(Integer id);
}
