package com.siemens.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class TrustDTO extends BaseDTO<Long> implements Serializable {
    private int gearing;
    private int premiumDiscount;

    @Override
    public String toString() {
        return "TrustDTO{" +
                "gearing=" + gearing +
                ", premiumDiscount=" + premiumDiscount +
                '}';
    }
}
