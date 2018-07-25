package com.siemens.web.controller;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyShare;
import com.siemens.core.model.SharePrice;
import com.siemens.core.service.SharePriceServiceInterface;
import com.siemens.web.converter.CompanyConverter;
import com.siemens.web.converter.CompanyShareConverter;
import com.siemens.web.converter.SharePriceConverter;
import com.siemens.web.dto.CompanyShareDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class SharePriceController {
    private static final Logger log = LoggerFactory.getLogger(SharePriceController.class);
    @Autowired
    private SharePriceServiceInterface sharePriceService;
    @Autowired
    private SharePriceConverter shareConverter;
    @Autowired
    private CompanyConverter companyConverter;
    @Autowired
    private CompanyShareConverter companyShareConverter;
    @RequestMapping(value = "/shareprice/{name}", method = RequestMethod.GET)
    public CompanyShareDTO getSharePrice(@PathVariable final String name)
    {
        log.trace(name+" --- Symbol in controller");
        CompanyShare companyShare = sharePriceService.getSharePrice( name.toUpperCase() );


        return companyShareConverter.convertModelToDto(companyShare);
    }
    @RequestMapping(value= "/iaopauza", method = RequestMethod.POST)
    public void getCompanyShare(@RequestBody final CompanyShareDTO companyShareDTO)
    {
        log.trace("-------> manualUpdate");
        Company company = companyConverter.convertDtoToModel(companyShareDTO.getCompany());
        SharePrice sharePrice = shareConverter.convertDtoToModel(companyShareDTO.getSharePrice());
        sharePriceService.manualSharePrice(company, sharePrice);
    }
}
