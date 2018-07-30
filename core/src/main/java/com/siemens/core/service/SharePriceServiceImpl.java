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
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public List<SharePrice> getAllPrices()
    {
        List<SharePrice> sharePrices = sharePriceRepository.findAll();
        sharePrices.sort(Comparator.comparing(SharePrice::getDate));
        return sharePrices;
    }
    @Override
    public void manualSharePrice(Company company, SharePrice sharePrice)
    {

        Currency currency = currencyRepository
                .findAll().stream().filter(c -> c.getSymbol().equals("EUR")).findFirst().get();


        Optional<Company> optionalCompany = companyRepository.findById(company.getId());
        if(optionalCompany.isPresent())
        {
            optionalCompany.get().setName(company.getName());
            optionalCompany.get().setDividendYield(company.getDividendYield());
            optionalCompany.get().setPE(company.getPE());
            optionalCompany.get().setCurrency(currency);
        }
        companyRepository.save(optionalCompany.get());

        Optional<SharePrice> optionalSharePrice = sharePriceRepository.findById(sharePrice.getId());
        if(optionalSharePrice.isPresent())
        {
            optionalSharePrice.get().setCompany(optionalCompany.get());
            optionalSharePrice.get().setDate(DateTime.now().toString("yyyy/MM/dd"));
            optionalSharePrice.get().setPrice(sharePrice.getPrice());
        }
        sharePriceRepository.save(optionalSharePrice.get());



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

            Currency currency;
            if(parameters[4].equals("null"))
                currency = currencyRepository
                        .findAll().stream().filter(c -> c.getSymbol().equals("EUR")).findFirst().get();
            else
                currency = currencyRepository
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
                dividendYield = Float.parseFloat("0");
                company.setDividendYield(dividendYield);
            }
            try{
                priceEarning = Double.parseDouble(parameters[3]);
                company.setPE(priceEarning);
            }catch (Exception e)
            {
                priceEarning = Double.parseDouble("0");
                company.setPE(priceEarning);
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
                sharePriceValue = Double.parseDouble("0");
                sharePrice.setPrice(sharePriceValue);
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
        Currency currency = currencyRepository
                .findAll().stream().filter(c -> c.getSymbol().equals("EUR")).findFirst().get();

        Company newComp = companyRepository.save(Company.builder()
                .name(symbol)
                .currency(currency)
                .build());
        SharePrice newShare = sharePriceRepository.save(
                SharePrice.builder().company(newComp).date(DateTime.now().toString()).price(Double.parseDouble("0"))
                        .build()
        );
        return CompanyShare.builder()
                .company(
                        newComp
                )
                .sharePrice(
                        newShare
                )
                .build();




    }
}
