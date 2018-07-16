package com.siemens.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtfDTO extends BaseDTO {

    private int type;

    @Override
    public String toString() {
        return "EtfDTO{" +
                "type=" + type +
                '}';
    }
}
