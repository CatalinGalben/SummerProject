package com.siemens.web.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SharePriceDTO extends BaseDTO {
    private CompanyDTO company;
    private int price;
    private DateTime date;

    @Override
    public String toString() {
        return "SharePriceDTO{" +
                "company=" + company +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
