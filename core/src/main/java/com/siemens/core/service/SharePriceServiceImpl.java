package com.siemens.core.service;

import com.siemens.core.api.Api;
import com.siemens.core.model.Company;
import com.siemens.core.model.Currency;
import com.siemens.core.model.SharePrice;
import com.siemens.core.repository.CompanyRepository;
import com.siemens.core.repository.CurrencyRepository;
import com.siemens.core.repository.SharePriceRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SharePriceServiceImpl implements SharePriceServiceInterface{
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SharePriceRepository sharePriceRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Override
    public SharePrice manualSharePrice(String symbol, Integer price, Double PE, Float dividend, String currencyName)
    {
        Currency currency = currencyRepository.findAll().stream()
                .filter(crr -> crr.getName().equals(currencyName)).findFirst().get();

        Company company = Company.builder()
                .PE(PE)
                .name(symbol)
                .dividendYield(dividend)
                .currency(currency)
                .build();
        companyRepository.save(company);

        SharePrice sharePrice = SharePrice.builder()
                .price(price)
                .date(DateTime.now())
                .company(company)
                .build();
        return sharePriceRepository.save(sharePrice);

    }
    @Override
    public SharePrice getSharePrice(String symbol)
    {
        //FIRST , check if share price already exists in the database, so the api wont need to interrogate
        Optional<SharePrice> optionalSharePrice = sharePriceRepository
                .findAll().stream().filter(sp -> sp.getCompany().getName().equals(symbol)).findFirst();
        if(optionalSharePrice.isPresent())
        {
            return optionalSharePrice.get();
        }

        String result = Api.Interogate(symbol);
        String[] parameters;

        if(!result.equals("Empty Response"))
        {
            parameters = result.split(";");



            Currency currency = currencyRepository
                    .findAll().stream().filter(c -> c.getName().equals(parameters[4])).findFirst().get();

            Company company = Company.builder()
                    .currency(currency)
                    .dividendYield(Float.parseFloat(parameters[2]))
                    .name(symbol)
                    .PE(Double.parseDouble(parameters[3]))
                    .build();

            companyRepository.save(company);// saving the company !!!! after creation!

            SharePrice sharePrice = SharePrice.builder()
                    .price(Integer.parseInt(parameters[1]))
                    .company(company)
                    .date(DateTime.now())
                    .build();
            return sharePriceRepository.save(sharePrice);



        }
        //in case of api not being able to determine the symbol
        //the user is going to have to input the desired data manually
        return SharePrice.builder()
                .date(DateTime.now())
                .price(0)
                .company(null)
                .build();





    }
}
