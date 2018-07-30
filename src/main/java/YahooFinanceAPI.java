import org.springframework.web.client.RestTemplate;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class YahooFinanceAPI {
    public static void main(String[] args){
//        List<Integer> list= new ArrayList<>();
//        list.add(1);
//        list.add(4);
//        list.add(8);
//        list.add(15);
//
//        Optional<Integer> a = list.stream().filter(x -> x == 4).findFirst();
//        if(a.isPresent())
//            System.out.println(a.get());
//
//        try{
//            Stock stock = YahooFinance.get("SIE.DE");
//            BigDecimal price = stock.getQuote().getPrice();
//            BigDecimal change = stock.getQuote().getChangeInPercent();
//            BigDecimal dividend = stock.getDividend().getAnnualYield();
//            BigDecimal pe = stock.getStats().getPe();
//
//            //This should be used if you want to print all the details provided
//            stock.print();
///*          System.out.println(stock.getName());
//            System.out.println("Price: " + price);
//            System.out.println("Change: " + change);
//            System.out.println("Dividend Yield: " + dividend);
//            System.out.println("Price to Earning Ration: " + pe);*/
//        }catch(Exception e)
//        {
//            System.out.println(e);
//        }

        RestTemplate restTemplate = new RestTemplate();
        String result =
                restTemplate.getForObject(
                        "https://data.fixer.io/api/latest" +
                                "?access_key=ea946dd8421ed294c58ff5b94c03072e" +
                                "&base=EUR" +
                                "&symbols=USD",
                        String.class,"42", "21"
                );
        System.out.println(result);



    }
}