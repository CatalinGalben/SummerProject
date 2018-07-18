package com.siemens.core.service;

import com.siemens.core.model.SharePrice;

public interface SharePriceServiceInterface {

    SharePrice getSharePrice(String symbol);

    SharePrice manualSharePrice(String symbol, Integer price, Double PE, Float dividend, String currencyName);
}
