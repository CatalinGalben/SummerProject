import org.springframework.web.client.RestTemplate;
import yahoofinance.Stock;
import yahoofinance.YahooFinance;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class YahooFinanceAPI {
    public static void main(String[] args){
        List<Integer> list= new ArrayList<>();
        list.add(1);
        list.add(4);
        list.add(8);
        list.add(15);

        Optional<Integer> a = list.stream().filter(x -> x == 4).findFirst();
        if(a.isPresent())
            System.out.println(a.get());

        try{
            Stock stock = YahooFinance.get("mah");
            BigDecimal price = stock.getQuote().getPrice();
            BigDecimal change = stock.getQuote().getChangeInPercent();
            BigDecimal dividend = stock.getDividend().getAnnualYieldPercent();
            BigDecimal pe = stock.getStats().getPe();

            //This should be used if you want to print all the details provided
            stock.print();
            System.out.println(stock.getName());
            System.out.println("Price: " + price);
            System.out.println("Change: " + change);
            System.out.println("Dividend Yield: " + dividend);
            System.out.println("Price to Earning Ration: " + pe);
        }catch(Exception e)
        {
            System.out.println(e);
        }

           RestTemplate restTemplate = new RestTemplate();
//        String result =
//                restTemplate.getForObject(
//                        "http://data.fixer.io/api/latest" +
//                                "?access_key=ea946dd8421ed294c58ff5b94c03072e" +
//                                "&base=EUR",
//                        String.class,"42", "21"
//                );
//        System.out.println(result);
//        JSONObject  object = new JSONObject(result);
//        JSONObject rates = object.getJSONObject("rates");
//        System.out.println("\n"+rates);
//        Map<String, Object> pairs = rates.toMap();
//        for (Map.Entry<String, Object> entry : pairs.entrySet())
//        {
//            System.out.println(entry.getKey() + "/" + Double.parseDouble(entry.getValue().toString()));
//        }
//

//        String result =
//                restTemplate.getForObject(
//                        "http://data.fixer.io/api/latest" +
//                                "?access_key=ea946dd8421ed294c58ff5b94c03072e" +
//                                "&base=EUR"+
//                                "&symbols=USD",
//
//                        String.class,"42", "21"
//                );
//        System.out.println(result);
//        DateTimeFormatter f = DateTimeFormat.forPattern("yyyy/MM/dd");
//        String dateTime = DateTime.now().toString("yyyy/MM/dd");
//        DateTime dateTime1 = f.parseDateTime(dateTime);
//        System.out.println(f.parseDateTime(dateTime).isEqual(f.parseDateTime(DateTime.now().toString("yyyy/MM/dd"))));
//

    }
}