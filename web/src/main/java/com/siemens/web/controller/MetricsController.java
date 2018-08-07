package com.siemens.web.controller;

import com.siemens.core.model.YearData;
import com.siemens.core.service.MetricsServiceInterface;
import com.siemens.web.dto.MetricsDTO;
import com.siemens.web.dto.PlainPriceWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class MetricsController {
    private static final Logger log = LoggerFactory.getLogger(MetricsController.class);
    @Autowired
    private MetricsServiceInterface metricService;

    @RequestMapping(value = "/metrics/update/{hKey}/{name}/{year}", method = RequestMethod.POST)
    public void alterMetrics(@PathVariable final Integer hKey,
                             @PathVariable final String name,
                             @PathVariable final Integer year,
                             @RequestBody final PlainPriceWrapper value)
    {
        metricService.addMetric(hKey, name,year, value.getNewBalanceValue());
    }

    @RequestMapping(value = "/metrics/{hKey}/{name}", method = RequestMethod.GET)
    public Set<MetricsDTO> getMetrics(@PathVariable final Integer hKey,
                                      @PathVariable final String name){
        List<YearData> yearDataList = metricService.getMetricForName(hKey, name);
        Set<MetricsDTO> metricsDTOS = new HashSet<>();
        yearDataList.forEach(yearData -> metricsDTOS.add(
                MetricsDTO.builder()
                        .holdingrecordId(yearData.getMetric().getHoldingRecord().getId())
                        .name(yearData.getMetric().getName())
                        .year(yearData.getYear())
                        .value(yearData.getValue())
                        .build()
        ));

        return metricsDTOS;

    }
}
