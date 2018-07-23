package com.siemens.core.service;

import com.siemens.core.model.Broker;

import java.util.List;

public interface BrokerServiceInterface {

    Broker findById(Integer id);
    List<Broker> getBrokers();

}
