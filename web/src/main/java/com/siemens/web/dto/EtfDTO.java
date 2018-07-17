package com.siemens.web.dto;

import lombok.*;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EtfDTO extends FundDTO {

    private int type;

    @Builder(builderMethodName = "etfDtoBuilder")
    public EtfDTO(float nav, float ter, int type)
    {
        super(nav, ter);
        this.type=type;
    }

    @Override
    public String toString() {
        return "EtfDTO{" +
                "type=" + type +
                '}';
    }
}
