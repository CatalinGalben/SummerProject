package com.siemens.core.service;

import com.siemens.core.model.Company;
import com.siemens.core.model.SharePrice;

import java.util.Map;

public interface SharePriceServiceInterface {

    Map<Company,SharePrice> getSharePrice(String symbol);

    SharePrice manualSharePrice(String symbol, Integer price, Double PE, Float dividend, String currencyName);
}
