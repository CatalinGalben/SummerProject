import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.math.BigDecimal;

public class YahooFinanceAPI {
    public static void main(String[] args){
        try{
            Stock stock = YahooFinance.get("SAHOL.IS");
            BigDecimal price = stock.getQuote().getPrice();
            BigDecimal change = stock.getQuote().getChangeInPercent();
            BigDecimal dividend = stock.getDividend().getAnnualYield();
            BigDecimal pe = stock.getStats().getPe();

            //This should be used if you want to print all the details provided
            //stock.print();
            System.out.println(stock.getName());
            System.out.println("Price: " + price);
            System.out.println("Change: " + change);
            System.out.println("Dividend Yield: " + dividend);
            System.out.println("Price to Earning Ration: " + pe);
        }catch(Exception e)
        {
            System.out.println(e);
        }


    }
}