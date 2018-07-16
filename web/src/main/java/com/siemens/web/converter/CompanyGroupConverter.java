package com.siemens.web.converter;

import com.siemens.core.model.Company;
import com.siemens.core.model.CompanyGroup;
import com.siemens.web.dto.CompanyGroupDTO;
import org.springframework.stereotype.Component;

@Component
public class CompanyGroupConverter extends AbstractConverterBaseEntity<CompanyGroup, CompanyGroupDTO> {

    @Override
    public CompanyGroupDTO convertModelToDto(CompanyGroup companyGroup){
        CompanyGroupDTO companyGroupDTO = CompanyGroupDTO.builder()
                .companyid(companyGroup.getCompany().getId())
                .groupid(companyGroup.getGroup().getId())
                .build();
        companyGroupDTO.setId(companyGroup.getId());
        return companyGroupDTO;
    }

    @Override
    public CompanyGroup convertDtoToModel(CompanyGroupDTO companyGroupDTO){
        //todo
        return null;
    }
}
