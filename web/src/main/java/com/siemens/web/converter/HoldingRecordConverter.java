package com.siemens.web.converter;

import com.siemens.core.model.HoldingRecord;
import com.siemens.web.dto.HoldingRecordDTO;
import org.springframework.stereotype.Component;

@Component
public class HoldingRecordConverter extends AbstractConverterBaseEntity<HoldingRecord, HoldingRecordDTO> {

    @Override
    public HoldingRecord convertDtoToModel(HoldingRecordDTO holdingRecordDTO){
        HoldingRecord holdingRecord = HoldingRecord.builder()
                .pricePaid(holdingRecordDTO.getPricePaid())
                .noShares(holdingRecordDTO.getNoShares())
                .broker(null)
                .company(null)
                .user(null)
                .build();
        holdingRecord.setId(holdingRecordDTO.getId());
        return holdingRecord;
    }

    @Override
    public HoldingRecordDTO convertModelToDto(HoldingRecord holdingRecord){
        HoldingRecordDTO holdingRecordDTO = HoldingRecordDTO.builder()
                .noShares(holdingRecord.getNoShares())
                .pricePaid(holdingRecord.getPricePaid())
                .brokerid(holdingRecord.getBroker().getId())
                .companyid(holdingRecord.getCompany().getId())
                .userid(holdingRecord.getUser().getId())
                .build();
        holdingRecordDTO.setId(holdingRecord.getId());
        return holdingRecordDTO;
    }
}
