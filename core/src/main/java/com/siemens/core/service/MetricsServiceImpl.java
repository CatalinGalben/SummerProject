package com.siemens.core.service;

import com.siemens.core.model.HoldingRecord;
import com.siemens.core.model.Metric;
import com.siemens.core.model.YearData;
import com.siemens.core.repository.HoldingRecordRepository;
import com.siemens.core.repository.MetricsRepository;
import com.siemens.core.repository.YearDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class MetricsServiceImpl implements MetricsServiceInterface {
    @Autowired
    private YearDataRepository yearDataRepository;
    @Autowired
    private MetricsRepository metricsRepository;
    @Autowired
    private HoldingRecordRepository hrRepo;
    @Override
    public List<YearData> getMetricForName(Integer hId) {
        return yearDataRepository
                .findAll()
                .stream()
                .filter(yd -> yd.getMetric().getHoldingRecord().getId().equals(hId))
                .collect(Collectors.toList());
    }
    @Override
    public List<YearData> getAllMetrics()
    {
        return yearDataRepository.findAll();
    }
    @Override
    @Transactional
    public void addMetric(Integer hId, String name, Integer year, Double value) {
        Optional<HoldingRecord> holdingRecord = hrRepo.findById(hId);

        Optional<Metric> optionalMetric = metricsRepository.findAll()
                .stream()
                .filter(m -> m.getName().equals(name) && m.getHoldingRecord().getId().equals(hId))
                .findFirst();

        if (optionalMetric.isPresent()) {
            Optional<YearData> optionalYearData = yearDataRepository.findAll()
                    .stream()
                    .filter(yd -> yd.getMetric().getId().equals(optionalMetric.get().getId()) && yd.getYear().equals(year))
                    .findFirst();
            if (optionalYearData.isPresent()) {
                optionalYearData.get().setValue(value);
                yearDataRepository.save(optionalYearData.get());
            } else {
                yearDataRepository.save(
                        YearData.builder()
                                .metric(optionalMetric.get())
                                .year(year)
                                .value(value)
                                .build()
                );
            }
        } else

        {
            Metric metric = metricsRepository.save(
                    Metric.builder()
                            .holdingRecord(holdingRecord.get())
                            .name(name)
                            .build()
            );

            yearDataRepository.save(
                    YearData.builder()
                            .metric(metric)
                            .year(year)
                            .value(value)
                            .build()
            );

        }
    }
}