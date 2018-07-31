package com.siemens.core.api;

import org.json.JSONObject;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

public class CurrencyApi {
    public static Map<String, Double> Interogate()
    {
        RestTemplate restTemplate = new RestTemplate();
        String result =
                restTemplate.getForObject(
                        "http://data.fixer.io/api/latest" +
                                "?access_key=ea946dd8421ed294c58ff5b94c03072e" +
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
        return exchanges;
    }
    public static Double exchange(String Symbol, Double value)
    {
        RestTemplate restTemplate = new RestTemplate();
        String result =
                restTemplate.getForObject(
                        "http://data.fixer.io/api/latest" +
                                "?access_key=ea946dd8421ed294c58ff5b94c03072e" +
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
        return value * 1/factor;

    }
}

