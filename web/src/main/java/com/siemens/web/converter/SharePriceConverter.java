package com.siemens.web.converter;

import com.siemens.core.model.SharePrice;
import com.siemens.web.dto.SharePriceDTO;
import org.springframework.stereotype.Component;

@Component
public class SharePriceConverter extends AbstractConverterBaseEntity<SharePrice, SharePriceDTO> {

    @Override
    public SharePrice convertDtoToModel(SharePriceDTO sharePriceDTO){
        SharePrice sharePrice = SharePrice.builder()
                .company(null)
                .price(sharePriceDTO.getPrice())
                .date(sharePriceDTO.getDate())
                .build();
        sharePrice.setId(sharePriceDTO.getId());
        return sharePrice;
    }

    @Override
    public SharePriceDTO convertModelToDto(SharePrice sharePrice){
        SharePriceDTO sharePriceDTO = SharePriceDTO.builder()
                .price(sharePrice.getPrice())
                .companyid(sharePrice.getCompany().getId())
                .date(sharePrice.getDate())
                .build();
        sharePriceDTO.setId(sharePrice.getId());
        return sharePriceDTO;
    }
}
