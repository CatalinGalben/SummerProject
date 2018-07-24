package com.siemens.web.dto;

import lombok.*;
import org.joda.time.DateTime;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharePriceDTO extends BaseDTO {
    private int companyid;
    private Double price;
    private String date;

    @Override
    public String toString() {
        return "SharePriceDTO{" +
                "companyid=" + companyid +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
