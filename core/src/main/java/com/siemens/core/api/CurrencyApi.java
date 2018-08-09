package com.siemens.core.api;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class CurrencyApi {
    private static final Logger log = LoggerFactory.getLogger(CurrencyApi.class);
    public static Map<String, Double> Interogate()
    {
        RestTemplate restTemplate = new RestTemplate();
        String result =
                restTemplate.getForObject(
                        "http://data.fixer.io/api/latest" +
                                "?access_key=3a0523b03b957ed31eb39a2e5317fdce" +
                                "&base=EUR"+
                                "&symbols=RON,EUR,USD,GBP",

                        String.class,"42", "21"
                );
        JSONObject object = new JSONObject(result);
        JSONObject rates = object.getJSONObject("rates");
        Map<String, Object> pairs = rates.toMap();
        Map<String, Double> exchanges = new HashMap<>();
        for (Map.Entry<String, Object> entry : pairs.entrySet())
        {

            exchanges.put(entry.getKey(),Double.parseDouble(entry.getValue().toString()));
        }
        log.trace("BASIC INTEROGATE #############################");
        return exchanges;
    }
    public static Double exchange(String Symbol, Double value)
    {
        RestTemplate restTemplate = new RestTemplate();
        String result =
                restTemplate.getForObject(
                        "http://data.fixer.io/api/latest" +
                                "?access_key=3a0523b03b957ed31eb39a2e5317fdce" +
                                "&base=EUR"+
                                "&symbols="+Symbol,

                        String.class,"42", "21"
                );
        JSONObject object = new JSONObject(result);
        JSONObject rates = object.getJSONObject("rates");
        Map<String, Object> pairs = rates.toMap();
        Double factor=0.0;
        for (Map.Entry<String, Object> entry : pairs.entrySet())
        {
            factor = Double.parseDouble(entry.getValue().toString());
        }
        log.trace("EXCHANGE INTERRROOOOGGAATE");
        return value * 1/factor;

    }
}

