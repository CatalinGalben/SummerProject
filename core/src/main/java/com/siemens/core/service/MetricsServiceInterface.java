package com.siemens.core.service;

import com.siemens.core.model.Metric;
import com.siemens.core.model.YearData;

import java.util.List;
import java.util.Set;

public interface MetricsServiceInterface {

    List<YearData> getMetricForName(Integer hId);
    void addMetric(Integer hId, String name, Integer year, Double value);
    List<YearData> getAllMetrics();

}
