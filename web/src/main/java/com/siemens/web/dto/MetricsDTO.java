package com.siemens.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MetricsDTO extends BaseDTO{
    private int holdingrecordid;
    private String name;
}
