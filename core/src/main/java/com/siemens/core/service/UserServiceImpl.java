package com.siemens.core.service;

import com.siemens.core.model.Broker;
import com.siemens.core.model.Company;
import com.siemens.core.model.HoldingRecord;
import com.siemens.core.model.User;
import com.siemens.core.repository.BrokerRepository;
import com.siemens.core.repository.CompanyRepository;
import com.siemens.core.repository.HoldingRecordRepository;
import com.siemens.core.repository.UserRepository;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
public class UserServiceImpl implements UserServiceInterface{
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompanyRepository companyRepository;
    @Autowired
    private BrokerRepository brokerRepository;
    @Autowired
    private HoldingRecordRepository holdingRecordRepository;
    @Override
    public User createUser(
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            String type,
            Double income,
            DateTime dob){
        User user = User.builder()
                .firstName( firstName )
                .lastName( lastName )
                .email( email )
                .type( type )
                .username( username )
                .password( password )
                .dob( dob )
                .balance( income )
                .build();
        return userRepository.save( user );
    }
    @Override
    public User findById(Integer key){
        return userRepository.getOne(key);
    }

    @Override
    public void addDividend(String symbol, Integer valueOfShare, Integer brokerKey, Integer userKey)
    {
        Company company = companyRepository.findAll().stream()
                .filter(c -> c.getName().equals(symbol)).findFirst().get();
        Broker broker = brokerRepository.getOne(brokerKey);

        HoldingRecord holdingRecord = holdingRecordRepository.findAll()
                .stream().filter(hr -> hr.getCompany().getName().equals(symbol)).findFirst().get();

        User user = userRepository.getOne(userKey);

        float lostMoney = (valueOfShare * holdingRecord.getNoShares()) * (broker.getDividendFee())/100;
        float earnings = (valueOfShare * holdingRecord.getNoShares()) - lostMoney;
        broker.setProfit(broker.getProfit() + lostMoney);
        user.setBalance(user.getBalance() + earnings);



    }
    @Override
    public User setAmountOfCash(Integer key, Integer value)
    {
        User user = userRepository.getOne(key);
        user.setBalance(value);

        return user;
    }

}
