package com.siemens.web.controller;

import com.siemens.core.service.BrokerServiceInterface;
import com.siemens.web.converter.BrokerConverter;
import com.siemens.web.dto.BrokerDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class BrokerController {

    private static final Logger log = LoggerFactory.getLogger(BrokerController.class);

    @Autowired
    private BrokerServiceInterface brokerService;
    @Autowired
    private BrokerConverter brokerConverter;

    @RequestMapping(value = "/brokers", method = RequestMethod.GET)
    public Set<BrokerDTO> getBrokers()
    {
        log.trace("getBrokers -- method entered - back-end");

        return (Set<BrokerDTO>)brokerConverter.convertModelsToDtos(brokerService.getBrokers());

    }

}
