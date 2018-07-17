package com.siemens.web.dto;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FundDTO extends BaseDTO{
    private float nav;
    private float ter;
    private int holdingRecordid;

    public FundDTO(float nav, float ter)
    {
        this.nav = nav;
        this.ter = ter;
    }

    @Override
    public String toString() {
        return "FundDTO{" +
                "nav=" + nav +
                ", ter=" + ter +
                ", holdingRecordid=" + holdingRecordid +
                '}';
    }
}





























































