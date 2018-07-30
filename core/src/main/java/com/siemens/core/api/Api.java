package com.siemens.core.api;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.math.BigDecimal;

public class Api {
    public static String Interogate(String symbol){
        String result = "Empty Response";

        try{
            Stock stock = YahooFinance.get(symbol);
            BigDecimal price = stock.getQuote().getPrice();
            BigDecimal change = stock.getQuote().getChangeInPercent();
            BigDecimal dividend = stock.getDividend().getAnnualYield();
            BigDecimal pe = stock.getStats().getPe();
            String currencySmbl = stock.getCurrency();
            String companyName = stock.getName();

            result = symbol + ";"
                    + price + ";"
                    + dividend + ";"
                    + pe + ";"
                    + currencySmbl + ";"
                    + companyName;

            //This should be used if you want to print all the details provided

/*          System.out.println(stock.getName());
            System.out.println("Price: " + price);
            System.out.println("Change: " + change);
            System.out.println("Dividend Yield: " + dividend);
            System.out.println("Price to Earning Ration: " + pe);*/
        }catch(Exception e)
        {
            return result;
        }
        return result;

    }
}
