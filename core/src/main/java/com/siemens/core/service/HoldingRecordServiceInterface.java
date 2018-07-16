package com.siemens.core.service;

import com.siemens.core.model.Broker;
import com.siemens.core.model.Company;
import com.siemens.core.model.HoldingRecord;
import com.siemens.core.model.User;

public interface HoldingRecordServiceInterface {
    void liquidate();
    HoldingRecord createRecord(User user, Broker broker, Company company, Double paidPrice, Integer noShares);
}
