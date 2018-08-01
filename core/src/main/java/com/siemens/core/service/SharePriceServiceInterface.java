package com.siemens.core.service;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyShare;
import com.siemens.core.model.SharePrice;

import java.util.List;
import java.util.Map;

public interface SharePriceServiceInterface {

    CompanyShare getSharePrice(String symbol);

    void manualSharePrice(Company company, SharePrice sharePrice);

    List<SharePrice> getAllPrices();

    void updateDailyPrices();
}
