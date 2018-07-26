package com.siemens.web.controller;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyShare;
import com.siemens.core.model.HoldingRecord;
import com.siemens.core.model.SharePrice;
import com.siemens.core.service.CompanyServiceInterface;
import com.siemens.core.service.HoldingRecordServiceInterface;
import com.siemens.core.service.SharePriceServiceInterface;
import com.siemens.web.converter.CompanyConverter;
import com.siemens.web.converter.CompanyShareConverter;
import com.siemens.web.converter.SharePriceConverter;
import com.siemens.web.dto.CompanyDTO;
import com.siemens.web.dto.CompanyShareDTO;
import com.siemens.web.dto.HoldingRecordDTO;
import com.siemens.web.dto.SharePriceDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;


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
    @Autowired
    private HoldingRecordServiceInterface holdingRecordService;
    @Autowired
    private CompanyServiceInterface companyService;
    @RequestMapping(value = "/shareprice/{name}", method = RequestMethod.GET)
    public CompanyShareDTO getSharePrice(@PathVariable final String name)
    {
        log.trace(name+" --- Symbol in controller");
        List<HoldingRecord> holdingRecords = holdingRecordService.getAllRecords();
        Optional<Company> company = companyService.getAllCompanies()
                .stream().filter(c-> c.getName().equals(name.toUpperCase())).findFirst();

        if(company.isPresent())
        {
            Optional<HoldingRecord> optionalRecord = holdingRecords.stream()
                    .filter(hr -> hr.getCompany().getId().equals(company.get().getId())).findFirst();
            if(optionalRecord.isPresent())
            {
                return CompanyShareDTO.builder()
                        .sharePrice(SharePriceDTO.builder().build())
                        .company(CompanyDTO.builder().build())
                        .build();
            }
        }

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
    @RequestMapping(value = "/shareprices", method = RequestMethod.GET)
    public Set<SharePriceDTO> getAllPrices()
    {
        return  (Set<SharePriceDTO>) shareConverter.convertModelsToDtos(sharePriceService.getAllPrices());
    }
}
