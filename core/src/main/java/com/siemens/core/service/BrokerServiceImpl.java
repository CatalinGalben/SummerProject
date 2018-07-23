package com.siemens.core.service;


import com.siemens.core.model.Broker;
import com.siemens.core.repository.BrokerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrokerServiceImpl implements BrokerServiceInterface {

    @Autowired
    private BrokerRepository brokerRepository;

    @Override
    public Broker findById(Integer id)
    {
        Optional<Broker> optionalBroker = brokerRepository.findById(id);
        if(optionalBroker.isPresent())
            return  optionalBroker.get();
        throw new RuntimeException("The Broker you are looking for does not exist!");
    }

    @Override
    public List<Broker> getBrokers()
    {
        return brokerRepository.findAll();
    }
}
