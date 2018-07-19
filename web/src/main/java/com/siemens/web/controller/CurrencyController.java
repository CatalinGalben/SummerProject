package com.siemens.web.controller;

import com.siemens.core.service.CurrencyServiceInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CurrencyController {
    private static final Logger log = LoggerFactory.getLogger(CurrencyController.class);
    @Autowired
    private CurrencyServiceInterface currencyService;

    @RequestMapping(value = "/currency", method = RequestMethod.GET)
    public String changeCurrency(@RequestBody final String currSymbol,
                          @RequestBody final String desiredSymbol,
                          @RequestBody final Double value){
        log.trace("User requested change of currency from "+currSymbol+ " to "+desiredSymbol);
        return currencyService.chooseCurrency(currSymbol, desiredSymbol, value);
    }
}
