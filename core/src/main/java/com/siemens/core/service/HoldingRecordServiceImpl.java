package com.siemens.core.service;

import com.siemens.core.model.*;
import com.siemens.core.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HoldingRecordServiceImpl implements HoldingRecordServiceInterface {

    @Autowired
    private HoldingRecordRepository holdingRecordRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private BrokerRepository brokerRepository;
    @Autowired
    private SharePriceRepository sharePriceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  UserServiceInterface userServiceInterface;
    @Autowired
    private FundRepository fundRepository;
    @Autowired
    private TrustRepository trustRepository;
    @Autowired
    private EtfRepository etfRepository;
    @Override
    public HoldingRecord createRecord
            (User user, Broker broker, Company company, Double paidPrice, Integer noShares)
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
    public  HoldingRecord createRecord
            (User user, Broker broker, Company company, Double paidPrice, Integer noShares, Float nav, Float ter,
             Float gearing, Float premium){

        Trust trust = Trust.trustBuilder()
                .nav(nav)
                .gearing(gearing)
                .premiumDiscount(premium)
                .ter(ter)
                .build();
        trust.setCompany(company);
        trustRepository.save(trust);
        fundRepository.save(trust);

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
     public  HoldingRecord createRecord
             (User user, Broker broker, Company company, Double paidPrice, Integer noShares,Float nav, Float ter, int type){
        Etf etf = Etf.etfBuilder()
                .nav(nav)
                .ter(ter)
                .type(type)
                .build();
        etf.setCompany(company);
        etfRepository.save(etf);
        fundRepository.save(etf);
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
    public List<HoldingRecord> getAllRecords()
    {
        return holdingRecordRepository.findAll();
    }

    @Override
    public List<HoldingRecord> addToRecord(Integer recordKey, Integer userKey, Integer noShares, Integer shareKey)
    {
        List<HoldingRecord> currentRecords = holdingRecordRepository.findAll();
        currentRecords.forEach(
                r -> {
                    if(r.getId().equals(recordKey))
                        r.setNoShares(r.getNoShares() + noShares);
                }
        );

        User user = userRepository.getOne(userKey);
        SharePrice share = sharePriceRepository.getOne(shareKey);
        user.setBalance(user.getBalance() - share.getPrice() * noShares);
        return currentRecords;
    }



    @Override
    public void liquidate(String symbol)
    {
        List<Company> listCompany = companyRepository.findAll();
        Optional<Company> companyOptional = listCompany.stream().filter(c -> c.getName().equals(symbol.toUpperCase()))
                .findFirst();
        Company company;

        company = companyOptional.get();
        Optional<HoldingRecord> optionalRecord = holdingRecordRepository
                .findAll().stream().filter(hr -> hr.getCompany().getId().equals(company.getId())).findFirst();

        HoldingRecord record = optionalRecord.get();

        Optional<User> optionalUser = userRepository
                .findAll().stream().filter(u->u.getId().equals(record.getUser().getId())).findFirst();
        User user = optionalUser.get();

        SharePrice sharePrice = sharePriceRepository
                .findAll().stream().filter(sp -> sp.getCompany().getId().equals(company.getId())).findFirst().get();

        user.setBalance(
                user.getBalance() + sharePrice.getPrice() * record.getNoShares()
        );
        holdingRecordRepository.delete(record);
    }
}
