package com.siemens.web.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TrustWrapper {
    private HoldingRecordDTO holdingRecord;
    private TrustDTO trust;

}
