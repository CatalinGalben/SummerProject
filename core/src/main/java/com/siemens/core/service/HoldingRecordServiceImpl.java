package com.siemens.core.service;

import com.siemens.core.model.Broker;
import com.siemens.core.model.Company;
import com.siemens.core.model.HoldingRecord;
import com.siemens.core.model.User;
import com.siemens.core.repository.BrokerRepository;
import com.siemens.core.repository.CompanyRepository;
import com.siemens.core.repository.HoldingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HoldingRecordServiceImpl implements HoldingRecordServiceInterface {

    @Autowired
    private HoldingRecordRepository holdingRecordRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private BrokerRepository brokerRepository;

    @Override
    public HoldingRecord createRecord(User user, Broker broker, Company company, Double paidPrice, Integer noShares)
    {
        return holdingRecordRepository.save(
                HoldingRecord.builder()
                .user(user)
                .broker(broker)
                .company(company)
                .pricePaid(paidPrice)
                .noShares(noShares)
                .build()
        );
    }

    @Override
    public void liquidate()
    {

    }
}
