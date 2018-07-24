package com.siemens.web.dto;

import com.siemens.core.model.Company;
import com.siemens.core.model.SharePrice;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class CompanyShareDTO extends BaseDTO{
    private CompanyDTO company;
    private SharePriceDTO sharePrice;
}
