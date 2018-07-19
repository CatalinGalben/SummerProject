package com.siemens.core.service;

public interface CurrencyServiceInterface {

    String chooseCurrency(String currentSymbol, String desiredSymbol, Double value);
}
