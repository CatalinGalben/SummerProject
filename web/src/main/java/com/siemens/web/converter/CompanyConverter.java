package com.siemens.web.converter;

import com.siemens.core.model.Company;
import com.siemens.web.dto.CompanyDTO;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CompanyConverter extends AbstractConverterBaseEntity<Company, CompanyDTO> {

    @Override
    public Company convertDtoToModel(CompanyDTO companyDTO) {
        Company company = Company.builder()
                .name(companyDTO.getName())
                .PE(companyDTO.getPE())
                .sharePrices(null)
                .dividendYield(companyDTO.getDividendYield())
                .currency(null)
                .build();
        company.setId(companyDTO.getId());
        return company;
    }

    @Override
    public CompanyDTO convertModelToDto(Company company){
        CompanyDTO companyDTO = CompanyDTO.builder()
                .name(company.getName())
                .PE(company.getPE())
                .currencyid(company.getCurrency().getId())
                .dividendYield(company.getDividendYield())
                .build();
        companyDTO.setId(company.getId());
        return companyDTO;
    }
}
