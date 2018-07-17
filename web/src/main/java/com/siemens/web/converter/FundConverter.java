package com.siemens.web.converter;

import com.siemens.core.model.Fund;
import com.siemens.web.dto.FundDTO;
import org.springframework.stereotype.Component;

@Component
public class FundConverter extends AbstractConverterBaseEntity<Fund, FundDTO> {

    @Override
    public Fund convertDtoToModel(FundDTO fundDTO){
        Fund fund = Fund.builder()
                .nav(fundDTO.getNav())
                .ter(fundDTO.getTer())
                .holdingRecord(null)
                .build();
        fund.setId(fundDTO.getId());
        return fund;
    }

    @Override
    public FundDTO convertModelToDto(Fund fund){
        FundDTO fundDTO = FundDTO.builder()
                .nav(fund.getNav())
                .ter(fund.getTer())
                .holdingRecordid(fund.getHoldingRecord().getId())
                .build();
        fundDTO.setId(fund.getId());
        return fundDTO;
    }
}
