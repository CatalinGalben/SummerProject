package com.siemens.web.converter;

import com.siemens.core.model.Trust;
import com.siemens.web.dto.TrustDTO;
import org.springframework.stereotype.Component;

@Component
public class TrustConverter extends AbstractConverterBaseEntity<Trust, TrustDTO>{

    @Override
    public Trust convertDtoToModel(TrustDTO trustDTO){
        Trust trust = Trust.trustBuilder()
                .nav(trustDTO.getNav())
                .ter(trustDTO.getTer())
                .gearing(trustDTO.getGearing())
                .premiumDiscount(trustDTO.getPremiumDiscount())
                .build();
        trust.setId(trustDTO.getId());
        return trust;
    }

    @Override
    public TrustDTO convertModelToDto(Trust trust){
        TrustDTO trustDTO = TrustDTO.trustDtoBuilder()
                .nav(trust.getNav())
                .ter(trust.getTer())
                .gearing(trust.getGearing())
                .premiumDiscount(trust.getPremiumDiscount())
                .build();
        trustDTO.setId(trust.getId());
        return trustDTO;
    }

}
