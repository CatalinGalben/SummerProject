package com.siemens.core.service;

import com.siemens.core.model.*;
import com.siemens.core.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public List<HoldingRecord> addToRecord(Integer brokerKey, Integer recordKey, Integer userKey, Integer noShares, Integer shareKey, Double pricePaid)
    {
        Optional<SharePrice> sharePrice = sharePriceRepository.findById(shareKey);
        Optional<Broker> broker = brokerRepository.findById(brokerKey);

        Optional<HoldingRecord> holdingRecord = holdingRecordRepository.findById(recordKey);
        Double lostMoney = (sharePrice.get().getPrice()*broker.get().getShareFee())*noShares;
        holdingRecord.get().setNoShares(holdingRecord.get().getNoShares() + noShares);
        holdingRecord.get().setPricePaid(holdingRecord.get().getPricePaid() + noShares * sharePrice.get().getPrice() + lostMoney);
        holdingRecordRepository.save(holdingRecord.get());



        broker.get().setProfit(lostMoney);
        User user = userRepository.getOne(userKey);

        user.setBalance(user.getBalance() - sharePrice.get().getPrice() * noShares - lostMoney);
        userRepository.save(user);
        brokerRepository.save(broker.get());

        return holdingRecordRepository.findAll();
    }



    @Override
    @Transactional
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

        optionalUser.get().setBalance(
                user.getBalance() + sharePrice.getPrice() * record.getNoShares()
        );
        userRepository.save(optionalUser.get());
        holdingRecordRepository.delete(optionalRecord.get());
    }
}
