import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class asdas {
    public static void main(String[] args){
        String key = "5G9AEN06TMIBRAAS";
        int timeout = 3000;
        try{
            Stock stock = YahooFinance.get("SIE.DE");
            BigDecimal price = stock.getQuote().getPrice();
            BigDecimal change = stock.getQuote().getChangeInPercent();
            BigDecimal peg = stock.getStats().getPeg();
            BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();

            stock.print();
        }catch(Exception e)
        {
            System.out.println(e);
        }


    }
}