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

    @Override
    public HoldingRecord createRecord(User user, Broker broker, Company company, Double paidPrice, Integer noShares)
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
    public List<HoldingRecord> getAllRecords()
    {
        return holdingRecordRepository.findAll();
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
