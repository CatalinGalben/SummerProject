import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.quotes.stock.StockQuote;

public class ExAttila {

    public static void main(String[] args) throws IOException {

        String[] symbols = new String[] { "INTC", "BABA", "TSLA", "AIR.PA", "YHOO", "SIE.DE" };

        ExAttila readMultipleStocks = new ExAttila();
        Map<String, Stock> results = readMultipleStocks.multiStock(symbols);

        results.forEach((k, v) -> {
            System.out.println("Symbol: " + k + " Stock: " + v);
            if (v != null) {
                System.out.println(v.getStats());
                System.out.println(v.getQuote().getPrice());
                System.out.println(v.getQuote().getOpen());
                System.out.println(v.getStats().getPe());
            }

        });
    }

    private Map<String, Stock> multiStock(String[] stocksSymbols) throws IOException {
        Map<String, Stock> stocks = YahooFinance.get(stocksSymbols); // single request

        return stocks;

    }

}
