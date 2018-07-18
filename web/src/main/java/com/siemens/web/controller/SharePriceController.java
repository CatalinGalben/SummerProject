package com.siemens.web.controller;

import com.siemens.core.model.SharePrice;
import com.siemens.core.service.SharePriceServiceInterface;
import com.siemens.web.converter.SharePriceConverter;
import com.siemens.web.dto.SharePriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SharePriceController {

    @Autowired
    private SharePriceServiceInterface sharePriceService;
    @Autowired
    private SharePriceConverter shareConverter;
    @RequestMapping(value = "/shareprice", method = RequestMethod.GET)
    public SharePriceDTO getSharePrice(@RequestBody final String name)
    {
        SharePrice sharePrice = sharePriceService.getSharePrice( name );

        return shareConverter.convertModelToDto(sharePrice);
    }
}
