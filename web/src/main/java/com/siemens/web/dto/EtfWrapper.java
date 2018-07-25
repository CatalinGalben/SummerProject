package com.siemens.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EtfWrapper {
    private HoldingRecordDTO holdingRecord;
    private EtfDTO etf;

}
