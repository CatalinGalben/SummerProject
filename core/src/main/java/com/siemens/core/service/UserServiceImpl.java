package com.siemens.core.service;

import com.siemens.core.model.Broker;
import com.siemens.core.model.Company;
import com.siemens.core.model.HoldingRecord;
import com.siemens.core.model.User;
import com.siemens.core.repository.BrokerRepository;
import com.siemens.core.repository.CompanyRepository;
import com.siemens.core.repository.HoldingRecordRepository;
import com.siemens.core.repository.UserRepository;
;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public User login(String username, String password)
    {
        LOG.trace("BACKEND LOGIN");
        Optional<User> optionalUser = userRepository
                .findAll().stream()
                .filter(u -> u.getUsername().equals(username) && u.getPassword().equals(password)).findFirst();
        if(optionalUser.isPresent()) {
            return optionalUser.get();

        }
            return User.builder()

                .build();
    }

    @Override
    public User createUser(
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            String type,
            Double income,
            String dob){

        LOG.trace("User Backend entered");
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
        return userRepository.findById(key).get();
    }

    @Override
    @Transactional
    public User addDividend(String symbol, Double valueOfShare, Integer brokerKey, Integer userKey)
    {
        Company company = companyRepository.findAll().stream()
                .filter(c -> c.getName().equals(symbol)).findFirst().get();
        float divYield = company.getDividendYield()/100;
        Optional<Broker> broker = brokerRepository.findById(brokerKey);

        HoldingRecord holdingRecord = holdingRecordRepository.findAll()
                .stream().filter(hr -> hr.getCompany().getName().equals(symbol)).findFirst().get();

        Optional<User> user = userRepository.findById(userKey);
        Integer noShares = holdingRecord.getNoShares();
        Double lostMoney = (valueOfShare*noShares*divYield)*(broker.get().getDividendFee());
        Double earnedMoney = valueOfShare*noShares*divYield;

        broker.get().setProfit(broker.get().getProfit() + lostMoney);
        user.get().setBalance(user.get().getBalance() + earnedMoney - lostMoney);
        userRepository.save(user.get());
        brokerRepository.save(broker.get());
        return user.get();

    }
    @Override
    @Transactional
    public User setAmountOfCash(Integer key, Double value)
    {
        Optional<User> user = userRepository.findById(key);
        user.get().setBalance(value);

        return userRepository.save(user.get());
    }

}
