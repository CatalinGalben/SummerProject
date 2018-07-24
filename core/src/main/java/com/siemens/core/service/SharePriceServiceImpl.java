package com.siemens.core.service;

import com.siemens.core.api.Api;
import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyShare;
import com.siemens.core.model.Currency;
import com.siemens.core.model.SharePrice;
import com.siemens.core.repository.CompanyRepository;
import com.siemens.core.repository.CurrencyRepository;
import com.siemens.core.repository.SharePriceRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class SharePriceServiceImpl implements SharePriceServiceInterface{
    private static final Logger log = LoggerFactory.getLogger(SharePriceServiceImpl.class);
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private SharePriceRepository sharePriceRepository;
    @Autowired
    private CurrencyRepository currencyRepository;
    @Override
    public void manualSharePrice(Company company, SharePrice sharePrice, String currencyName)
    {
        Currency currency = currencyRepository.findAll().stream()
                .filter(crr -> crr.getName().equals(currencyName)).findFirst().get();

        company.setCurrency(currency);
        companyRepository.save(company);

        sharePrice.setDate(DateTime.now().toString());
        sharePriceRepository.save(sharePrice);

    }
    @Override
    public CompanyShare getSharePrice(String symbol)
    {
        //FIRST , check if share price already exists in the database, so the api wont need to interrogate

        Float dividendYield = null;
        Double priceEarning = null;
        Double sharePriceValue = null;
        Optional<SharePrice> optionalSharePrice = sharePriceRepository
                .findAll().stream().filter(sp -> sp.getCompany().getName().equals(symbol)).findFirst();
        if(optionalSharePrice.isPresent())
        {

            return CompanyShare.builder()
                    .company(optionalSharePrice.get().getCompany())
                    .sharePrice(optionalSharePrice.get())
                    .build();
        }

        String result = Api.Interogate(symbol.toUpperCase());
        log.trace(result + "API RESPONSE ");
        String[] parameters;

        if(!result.equals("Empty Response"))
        {

            parameters = result.split(";");


            Currency currency = currencyRepository
                    .findAll().stream().filter(c -> c.getSymbol().equals(parameters[4])).findFirst().get();
            Company company = Company.builder()
                    .currency(currency)
                    .name(symbol)
                    .build();
            try{
                dividendYield = Float.parseFloat(parameters[2]);
                company.setDividendYield(dividendYield);
            }catch (Exception e)
            {
                dividendYield = null;
            }
            try{
                priceEarning = Double.parseDouble(parameters[3]);
                company.setPE(priceEarning);
            }catch (Exception e)
            {
                priceEarning = null;
            }



            log.trace("CREATED COMPANY! "+ company);
            Company company1 = companyRepository.save(company);// saving the company !!!! after creation!

            SharePrice sharePrice = SharePrice.builder()
                    .company(company1)
                    .date(DateTime.now().toString())
                    .build();


            try{
                sharePriceValue= Double.parseDouble(parameters[1]);
                sharePrice.setPrice(sharePriceValue);
            }catch (Exception e)
            {
                sharePriceValue = null;
            }

            log.trace("Created SHARE PRICE! "+ sharePrice);
            SharePrice sharePrice1 = sharePriceRepository.save(sharePrice);


            return CompanyShare.builder()
                    .company(company1)
                    .sharePrice(sharePrice1)
                    .build();
        }
        //in case of api not being able to determine the symbol
        //the user is going to have to input the desired data manually
        return CompanyShare.builder()
                .company(
                        Company.builder().build()
                )
                .sharePrice(
                        SharePrice.builder().build()
                )
                .build();




    }
}
