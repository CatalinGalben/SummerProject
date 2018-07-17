package com.siemens.web.dto;

import com.siemens.core.model.Fund;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TrustDTO extends FundDTO {
    private float gearing;
    private float premiumDiscount;

    @Builder
    public TrustDTO (float nav, float ter, float gearing, float premiumDiscount){
        super(nav, ter);
        this.gearing = gearing;
        this.premiumDiscount = premiumDiscount;
    }

    @Override
    public String toString() {
        return "TrustDTO{" +
                "gearing=" + gearing +
                ", premiumDiscount=" + premiumDiscount +
                '}';
    }
}
