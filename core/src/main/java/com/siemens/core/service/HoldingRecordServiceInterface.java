package com.siemens.core.service;

import com.siemens.core.model.Broker;
import com.siemens.core.model.Company;
import com.siemens.core.model.HoldingRecord;
import com.siemens.core.model.User;

import java.util.List;

public interface HoldingRecordServiceInterface {
    void liquidate(String symbol);
    HoldingRecord createRecord(User user, Broker broker, Company company, Double paidPrice, Integer noShares);
    List<HoldingRecord> getAllRecords();

}
