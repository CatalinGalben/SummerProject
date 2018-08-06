package com.siemens.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricsDTO extends BaseDTO{
    private Integer holdingrecordId;
    private String name;
    private String year;
    private Double value;
}
