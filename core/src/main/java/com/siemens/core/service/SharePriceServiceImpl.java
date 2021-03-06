package com.siemens.core.service;

import com.siemens.core.api.Api;
import com.siemens.core.api.CurrencyApi;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Transactional
    public void updateManualPrice(Integer compId, Double price)
    {
        Optional<SharePrice> optionalSharePrice = sharePriceRepository.findAll()
                .stream().filter(sp -> sp.getCompany().getId().equals(compId)).findFirst();
        sharePriceRepository.save(
                SharePrice.builder()
                .company(optionalSharePrice.get().getCompany())
                .date(DateTime.now().toString("yyyy/MM/dd"))
                .price(price)
                .build()
        );
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
    @Transactional
    public void updateDailyPrices()
    {
        List<SharePrice> sharePrices = sharePriceRepository.findAll()
                .stream()
                .sorted(Comparator.comparing(SharePrice::getId).reversed()).collect(Collectors.toList());
        Optional<SharePrice> optionalSharePrice =
                sharePrices.stream().filter(
                        sharePrice -> !Api.Interogate(sharePrice.getCompany().getName()).equals("Empty Response") &&
                                sharePrice.getDate().equals(DateTime.now().toString("yyyy/MM/dd"))

                ).findFirst();
        if(optionalSharePrice.isPresent())
        {
            log.trace("SORRY , UPDATED AVAILABLE SHAREPRICES TODAY");
            return;
        }
        SharePrice lastPrice = sharePrices.stream().findFirst().get();
        sharePrices = sharePrices.stream().filter(sharePrice -> sharePrice.getDate().equals(lastPrice.getDate())).collect(Collectors.toList());
        sharePrices.forEach(
                sharePrice -> {
                    String apiResponse = Api.Interogate(sharePrice.getCompany().getName());
                    if(!apiResponse.equals("Empty Response"))
                    {
                        String[] parameters = apiResponse.split(";");
                        Double sharePriceValue;

                        if(!parameters[4].equals("EUR") && !parameters[4].equals("GBp") && !parameters[4].equals("null"))
                            sharePriceValue = CurrencyApi.exchange(parameters[4], Double.parseDouble(parameters[1]));
                        else if (parameters[1].equals("null") && parameters[4].equals("null"))
                            return;
                        else if (parameters[4].equals("GBp"))
                            sharePriceValue = CurrencyApi.exchange(parameters[4], Double.parseDouble(parameters[1])/100);
                        else
                            sharePriceValue = Double.parseDouble(parameters[1]);

                        sharePriceRepository.save(
                                SharePrice.builder()
                                        .price(sharePriceValue)
                                        .company(sharePrice.getCompany())
                                        .date(DateTime.now().toString("yyyy/MM/dd"))
                                        .build()
                        );

                    }
                }
        );
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

            currency = currencyRepository
                    .findAll().stream().filter(c -> c.getSymbol().equals("EUR")).findFirst().get();


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
                    .date(DateTime.now().toString("yyyy/MM/dd"))
                    .build();


            try{
                //{possible limitation, if the user buys a ton of companies that are not in EUR currency,
                //the api 1000 query limit will be reached and adding more will not work
                //If we are talking about a real life situation , an api key will be included in the price of the product
                if(!parameters[4].equals("EUR") && !parameters[4].equals("GBp") && !parameters[4].equals("null"))
                    sharePriceValue = CurrencyApi.exchange(parameters[4], Double.parseDouble(parameters[1]));
                else if (parameters[4].equals("GBp"))
                    sharePriceValue = CurrencyApi.exchange(parameters[4], Double.parseDouble(parameters[1])/100);
                else
                    sharePriceValue = Double.parseDouble(parameters[1]);
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
                SharePrice.builder().company(newComp).date(DateTime.now().toString("yyyy/MM/dd")).price(Double.parseDouble("0"))
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
