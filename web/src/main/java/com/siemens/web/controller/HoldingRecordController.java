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
import com.siemens.web.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(value = "/holdingrecords/add", method = RequestMethod.POST)
    public HoldingRecordDTO createHoldingRecord(@RequestBody final HoldingRecordDTO holdingRecordDTO)
    {
        log.trace(" user has requested to buy shares (just plain shares in company)");
        Company company = companyServiceInterface.findById(holdingRecordDTO.getCompanyid());
        Broker broker = brokerServiceInterface.findById(holdingRecordDTO.getBrokerid());
        User user = userServiceInterface.findById(holdingRecordDTO.getUserid());
        HoldingRecord savedHoldingRecord = holdingRecordServiceInterface
                .createRecord(user, broker, company, holdingRecordDTO.getPricePaid(), holdingRecordDTO.getNoShares());
        return holdingRecordConverter.convertModelToDto(savedHoldingRecord);
    }

    @RequestMapping(value = "/holdingrecords/addtrust", method = RequestMethod.POST)
    public HoldingRecordDTO createHoldingRecord(@RequestBody final TrustWrapper trustWrapper)
    {
        HoldingRecordDTO holdingRecord = trustWrapper.getHoldingRecord();
        TrustDTO trust = trustWrapper.getTrust();
        log.trace(" user has requested to buy shares (TRUST)");
        Company company = companyServiceInterface.findById(holdingRecord.getCompanyid());
        Broker broker = brokerServiceInterface.findById(holdingRecord.getBrokerid());
        User user = userServiceInterface.findById(holdingRecord.getUserid());
        HoldingRecord savedHoldingRecord = holdingRecordServiceInterface
                .createRecord
                        (user, broker, company, holdingRecord.getPricePaid(), holdingRecord.getNoShares(),trust.getNav(),
                                trust.getTer(), trust.getGearing(), trust.getPremiumDiscount());
        return holdingRecordConverter.convertModelToDto(savedHoldingRecord);
    }

    @RequestMapping(value = "/holdingrecords/addetf", method = RequestMethod.POST)
    public HoldingRecordDTO createHoldingRecord(@RequestBody final EtfWrapper etfWrapper)
    {
        HoldingRecordDTO holdingRecordDTO = etfWrapper.getHoldingRecord();
        EtfDTO etfDTO = etfWrapper.getEtf();
        log.trace(" user has requested to buy shares (ETF)");
        Company company = companyServiceInterface.findById(holdingRecordDTO.getCompanyid());
        Broker broker = brokerServiceInterface.findById(holdingRecordDTO.getBrokerid());
        User user = userServiceInterface.findById(holdingRecordDTO.getUserid());
        HoldingRecord savedHoldingRecord = holdingRecordServiceInterface
                .createRecord(user, broker, company, holdingRecordDTO.getPricePaid(), holdingRecordDTO.getNoShares(),
                        etfDTO.getNav(), etfDTO.getTer(), etfDTO.getType());
        return holdingRecordConverter.convertModelToDto(savedHoldingRecord);
    }


    @RequestMapping(value = "/records/liquidate/{symbol}", method = RequestMethod.DELETE)
    public void liquidateRecord(@PathVariable final String symbol)
    {
        log.trace(" user has requested to liquidate all the shares corresponding to: " + symbol);
        holdingRecordServiceInterface.liquidate(symbol);
    }

    @RequestMapping(value = "/records/addshares/{userKey}/{shareKey}/{recordKey}/{pricePaid}", method = RequestMethod.PUT)
    public Set<HoldingRecordDTO> addToRecord(
            @PathVariable final int userKey, @PathVariable final int shareKey,
            @PathVariable final int recordKey, @PathVariable final int pricePaid,
            @RequestBody final int noShares
    ){
        List<HoldingRecord> updatedRecords = holdingRecordServiceInterface.
                addToRecord(recordKey, userKey, noShares, shareKey, pricePaid);

        return new HashSet<>(holdingRecordConverter.convertModelsToDtos(updatedRecords));
    }
}
