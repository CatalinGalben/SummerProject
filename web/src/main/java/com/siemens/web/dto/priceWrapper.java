package com.siemens.web.dto;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class priceWrapper {
    private Double pricePaid;
    private Integer noShares;
}
