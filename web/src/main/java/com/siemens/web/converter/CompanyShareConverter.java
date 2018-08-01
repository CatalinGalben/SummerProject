package com.siemens.web.converter;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyShare;
import com.siemens.core.model.SharePrice;
import com.siemens.web.dto.CompanyDTO;
import com.siemens.web.dto.CompanyShareDTO;
import com.siemens.web.dto.SharePriceDTO;
import org.springframework.stereotype.Component;

@Component
public class CompanyShareConverter extends AbstractConverterBaseEntity<CompanyShare, CompanyShareDTO> {
    @Override
    public CompanyShare convertDtoToModel(CompanyShareDTO companyShareDTO)
    {
        CompanyShare companyShare = CompanyShare.builder()
                .sharePrice(
                        SharePrice.builder()
                                .company(null)
                                .price(companyShareDTO.getSharePrice().getPrice())
                                .date(companyShareDTO.getSharePrice().getDate())
                                .build()

                )
                .company(
                        Company.builder()
                                .name(companyShareDTO.getCompany().getName())
                                .PE(companyShareDTO.getCompany().getPE())
                                .sharePrices(null)
                                .dividendYield(companyShareDTO.getCompany().getDividendYield())
                                .currency(null)
                                .build()
                )
                .build();
        companyShare.getSharePrice().setId(companyShareDTO.getSharePrice().getId());
        companyShare.getCompany().setId(companyShareDTO.getCompany().getId());
        return companyShare;
    }
    @Override
    public CompanyShareDTO convertModelToDto(CompanyShare companyShare)
    {
        CompanyShareDTO companyShareDTO = CompanyShareDTO.builder()
                .sharePrice(
                        SharePriceDTO.builder()
                                .price(companyShare.getSharePrice().getPrice())
                                .companyid(companyShare.getSharePrice().getCompany().getId())
                                .date(companyShare.getSharePrice().getDate())
                                .build()
                )
                .company(
                        CompanyDTO.builder()
                                .name(companyShare.getCompany().getName())
                                .PE(companyShare.getCompany().getPE())
                                .currencyid(companyShare.getCompany().getCurrency().getId())
                                .dividendYield(companyShare.getCompany().getDividendYield())
                                .build()
                )
                .build();
        companyShareDTO.getSharePrice().setId(companyShare.getSharePrice().getId());
        companyShareDTO.getCompany().setId(companyShare.getCompany().getId());
        return companyShareDTO;
    }

}
