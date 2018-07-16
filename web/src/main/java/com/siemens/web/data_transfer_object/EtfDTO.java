package com.siemens.web.data_transfer_object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EtfDTO extends BaseDTO<Long> implements Serializable {

    private int type;

    @Override
    public String toString() {
        return "EtfDTO{" +
                "type=" + type +
                '}';
    }
}
