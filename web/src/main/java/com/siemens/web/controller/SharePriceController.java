package com.siemens.web.controller;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyShare;
import com.siemens.core.model.SharePrice;
import com.siemens.core.service.SharePriceServiceInterface;
import com.siemens.web.converter.CompanyConverter;
import com.siemens.web.converter.CompanyShareConverter;
import com.siemens.web.converter.SharePriceConverter;
import com.siemens.web.dto.CompanyShareDTO;
import com.siemens.web.dto.SharePriceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Tuple;
import java.util.HashMap;
import java.util.Map;

@RestController
public class SharePriceController {

    @Autowired
    private SharePriceServiceInterface sharePriceService;
    @Autowired
    private SharePriceConverter shareConverter;
    @Autowired
    private CompanyConverter companyConverter;
    @Autowired
    private CompanyShareConverter companyShareConverter;
    @RequestMapping(value = "/shareprice", method = RequestMethod.GET)
    public CompanyShareDTO getSharePrice(@RequestBody final String name)
    {
        CompanyShare companyShare = sharePriceService.getSharePrice( name );


        return companyShareConverter.convertModelToDto(companyShare);
    }
    @RequestMapping(value= "/iaopauza", method = RequestMethod.POST)
    public void getCompanyShare(@RequestBody final CompanyShareDTO companyShareDTO, @RequestBody final String currencyName)
    {
        Company company = companyConverter.convertDtoToModel(companyShareDTO.getCompany());
        SharePrice sharePrice = shareConverter.convertDtoToModel(companyShareDTO.getSharePrice());
        sharePriceService.manualSharePrice(company, sharePrice, currencyName);
    }
}
