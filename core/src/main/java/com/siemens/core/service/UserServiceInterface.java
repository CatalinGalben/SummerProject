package com.siemens.core.service;

import com.siemens.core.model.User;


public interface UserServiceInterface {
    User createUser(
            String firstName,
            String lastName,
            String email,
            String username,
            String password,
            String type,
            Double income,
            String dob);
    User findById(Integer key);

    User setAmountOfCash(Integer id,Double value);

    User addDividend(String symbol, Double earnedMoney, Integer userKey);

    User login(String username, String password);

}
