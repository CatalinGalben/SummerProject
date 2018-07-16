package com.siemens.web.converter;

import com.siemens.core.model.Company;
import com.siemens.web.dto.CompanyDTO;
import org.springframework.stereotype.Component;

@Component
public class CompanyConverter extends AbstractConverterBaseEntity<Company, CompanyDTO> {

    @Override
    public Company convertDtoToModel(CompanyDTO companyDTO) {
        Company company = Company.builder()
                .name(companyDTO.getName())
                .PE(companyDTO.getPE())
                .dividendYield(companyDTO.getDividendYield())
                .build();
        company.setId(companyDTO.getId());
        return company;
    }

    @Override
    public CompanyDTO convertModelToDto(Company company){
        CompanyDTO companyDTO = CompanyDTO.builder()
                .name(company.getName())
                .PE(company.getPE())
                .dividendYield(company.getDividendYield())
                .build();
        companyDTO.setId(company.getId());
        return companyDTO;
    }
}
