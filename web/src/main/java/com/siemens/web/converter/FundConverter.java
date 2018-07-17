package com.siemens.web.converter;

import com.siemens.core.model.Fund;
import com.siemens.web.dto.FundDTO;
import org.springframework.stereotype.Component;

@Component
public class FundConverter extends AbstractConverterBaseEntity<Fund, FundDTO> {

    @Override
    public Fund convertDtoToModel(FundDTO fundDTO){
        Fund fund = Fund.fundBuilder()
                .nav(fundDTO.getNav())
                .ter(fundDTO.getTer())
                .build();
        fund.setId(fundDTO.getId());
        return fund;
    }

    @Override
    public FundDTO convertModelToDto(Fund fund){
        FundDTO fundDTO = FundDTO.builder()
                .nav(fund.getNav())
                .ter(fund.getTer())
                .build();
        fundDTO.setId(fund.getId());
        return fundDTO;
    }
}
