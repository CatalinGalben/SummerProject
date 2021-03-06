package com.siemens.web.controller;

import com.siemens.core.model.Broker;
import com.siemens.core.model.Company;
import com.siemens.core.model.HoldingRecord;
import com.siemens.core.model.User;
import com.siemens.core.service.*;
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
    @Autowired
    private SharePriceServiceInterface sharePriceServiceInterface;

    @RequestMapping(value = "/records", method = RequestMethod.GET)
    public Set<HoldingRecordDTO> getRecords()
    {
        List<HoldingRecord> records = holdingRecordServiceInterface.getAllRecords();

        return new HashSet<>(holdingRecordConverter.convertModelsToDtos(records));
    }
    @RequestMapping(value = "/records/{companyId}", method = RequestMethod.PUT)
    public void updateManualSharePrice(
            @PathVariable final Integer companyId, @RequestBody final PlainPriceWrapper plainPriceWrapper)
    {
        sharePriceServiceInterface.updateManualPrice(companyId, plainPriceWrapper.getNewBalanceValue());
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


    @RequestMapping(value = "/records/liquidate/{symbol}", method = RequestMethod.PUT)
    public void liquidateRecord(@PathVariable final String symbol, @RequestBody final priceWrapper noShares)
    {
        log.trace(" user has requested to liquidate all the shares corresponding to: " + symbol);
        holdingRecordServiceInterface.liquidate(symbol, noShares.getNoShares());
    }

    @RequestMapping(value = "/records/addshares/{brokerKey}/{userKey}/{shareKey}/{recordKey}", method = RequestMethod.PUT)
    public Set<HoldingRecordDTO> addToRecord( @PathVariable final int brokerKey,
            @PathVariable final int userKey, @PathVariable final int shareKey,
            @PathVariable final int recordKey,
            @RequestBody final priceWrapper priceWrapper
    ){

        Double pricePaid = priceWrapper.getPricePaid();
        Integer noShares = priceWrapper.getNoShares();
        log.trace("### User has requested to add shares to existing ones! ###");
        List<HoldingRecord> updatedRecords = holdingRecordServiceInterface.
                addToRecord(brokerKey, recordKey, userKey, noShares, shareKey, pricePaid);

        return (Set<HoldingRecordDTO>) holdingRecordConverter.convertModelsToDtos(updatedRecords);
    }
}
