package com.siemens.web.controller;

import com.siemens.core.model.*;
import com.siemens.core.repository.BrokerRepository;
import com.siemens.core.repository.CompanyRepository;
import com.siemens.core.repository.HoldingRecordRepository;
import com.siemens.core.service.BrokerServiceInterface;
import com.siemens.core.service.CompanyServiceInterface;
import com.siemens.core.service.HoldingRecordServiceInterface;
import com.siemens.core.service.UserServiceInterface;
import com.siemens.web.converter.HoldingRecordConverter;
import com.siemens.web.dto.HoldingRecordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class HoldingRecordController {
    private static final Logger log = LoggerFactory.getLogger(HoldingRecordController.class);

    @Autowired
    private CompanyServiceInterface companyServiceInterface;
    @Autowired
    private BrokerServiceInterface brokerServiceInterface;
    @Autowired
    private HoldingRecordServiceInterface holdingRecordServiceInterface;
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private HoldingRecordConverter holdingRecordConverter;

    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public Set<HoldingRecordDTO> getRecords()
    {
        List<HoldingRecord> records = holdingRecordServiceInterface.getAllRecords();

        return new HashSet<>(holdingRecordConverter.convertModelsToDtos(records));
    }

    @RequestMapping(value = "/records/add", method = RequestMethod.POST)
    public HoldingRecordDTO createHoldingRecord(@RequestBody final HoldingRecordDTO holdingRecordDTO)
    {
        Company company = companyServiceInterface.findById(holdingRecordDTO.getCompanyid()); //modify to get ids
        Broker broker = brokerServiceInterface.findById(holdingRecordDTO.getBrokerid());
        User user = userServiceInterface.findById(holdingRecordDTO.getUserid());
        HoldingRecord savedHoldingRecord = holdingRecordServiceInterface
                .createRecord(user, broker, company, holdingRecordDTO.getPricePaid(), holdingRecordDTO.getNoShares());
        return holdingRecordConverter.convertModelToDto(savedHoldingRecord);
    }


    @RequestMapping(value = "/records/liquidate", method = RequestMethod.DELETE)
    public void liquidateRecord(String symbol)
    {
       holdingRecordServiceInterface.liquidate(symbol);
    }
}
